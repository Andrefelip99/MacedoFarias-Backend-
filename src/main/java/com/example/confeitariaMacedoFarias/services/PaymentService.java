package com.example.confeitariaMacedoFarias.services;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.confeitariaMacedoFarias.dto.PaymentResponseDTO;
import com.example.confeitariaMacedoFarias.entities.Order;
import com.example.confeitariaMacedoFarias.entities.Payment;
import com.example.confeitariaMacedoFarias.entities.PaymentStatus;
import com.example.confeitariaMacedoFarias.entities.StatusOrder;
import com.example.confeitariaMacedoFarias.repositories.OrderRepository;
import com.example.confeitariaMacedoFarias.repositories.PaymentRepository;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final WhatsAppService whatsAppService;

    @Transactional
    public PaymentResponseDTO createPixPayment(Order order) throws Exception {

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest request =
                PaymentCreateRequest.builder()
                        .transactionAmount(order.getTotal())
                        .description("Pedido Confeitaria Macedo Farias")
                        .paymentMethodId("pix")
                        .payer(
                                PaymentPayerRequest.builder()
                                        .email("cliente@email.com")
                                        .build())
                        .build();

        com.mercadopago.resources.payment.Payment mpPayment = client.create(request);

        String qrCode = mpPayment
                .getPointOfInteraction()
                .getTransactionData()
                .getQrCode();

        String qrCodeBase64 = mpPayment
                .getPointOfInteraction()
                .getTransactionData()
                .getQrCodeBase64();

        Payment payment = new Payment();

        payment.setMercadoPagoPaymentId(mpPayment.getId().toString());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentMethod("PIX");
        payment.setQrCode(qrCode);
        payment.setQrCodeBase64(qrCodeBase64);
        payment.setAmount(order.getTotal());
        payment.setCreatedAt(Instant.now());
        payment.setOrder(order);

        paymentRepository.save(payment);

        return new PaymentResponseDTO(
                qrCode,
                qrCodeBase64,
                payment.getStatus().name()
        );
    }

    @Transactional
    public void updatePaymentStatus(String mercadoPagoPaymentId) throws Exception {
        Payment payment = paymentRepository.findByMercadoPagoPaymentId(mercadoPagoPaymentId);
        if (payment == null) {
            return; // Payment not found, ignore
        }

        PaymentClient client = new PaymentClient();
        com.mercadopago.resources.payment.Payment mpPayment = client.get(Long.valueOf(mercadoPagoPaymentId));

        PaymentStatus newStatus;
        String status = mpPayment.getStatus().toString();
        switch (status) {
            case "approved":
                newStatus = PaymentStatus.APPROVED;
                payment.setApprovedAt(Instant.now());
                
                // Atualizar status do pedido para APPROVED
                Order order = payment.getOrder();
                order.setStatus(StatusOrder.APPROVED);
                orderRepository.save(order);
                
                // Enviar mensagem WhatsApp
                sendWhatsAppMessage(order);
                break;
            case "rejected":
                newStatus = PaymentStatus.REJECTED;
                break;
            case "cancelled":
                newStatus = PaymentStatus.CANCELLED;
                break;
            default:
                newStatus = PaymentStatus.PENDING;
        }

        payment.setStatus(newStatus);
        paymentRepository.save(payment);
    }

    private void sendWhatsAppMessage(Order order) {
        String clientName = order.getClient().getName();
        String phone = order.getClient().getPhoneNumber();
        String address = order.getClient().getAddress();
        
        // Assumindo que há apenas um item no pedido, ou concatenar todos
        String product = order.getItems().stream()
                .map(item -> item.getProduct().getName() + " (x" + item.getQuantity() + ")")
                .reduce((a, b) -> a + ", " + b)
                .orElse("N/A");
        
        String value = order.getTotal().toString();
        
        whatsAppService.sendOrderMessage(clientName, phone, address, product, value);
    }
}

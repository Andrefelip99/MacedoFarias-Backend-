package com.example.confeitariaMacedoFarias.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.confeitariaMacedoFarias.dto.OrderInsertDto;
import com.example.confeitariaMacedoFarias.dto.OrderResponseDto;
import com.example.confeitariaMacedoFarias.entities.Client;
import com.example.confeitariaMacedoFarias.entities.DeliveryType;
import com.example.confeitariaMacedoFarias.entities.Order;
import com.example.confeitariaMacedoFarias.entities.StatusOrder;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.ClientRepository;
import com.example.confeitariaMacedoFarias.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    @Value("${delivery.fee:6.0}")
    private double deliveryFeeValue;

    @Transactional
    public OrderResponseDto insert(OrderInsertDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResourceNotFoundException("Usuário não autenticado");
        }

        String email = authentication.getName();
        System.out.println("EMAIL TOKEN: " + email);

        Client client = clientRepository.findAll().stream()
                .filter(c -> c.getEmail().trim().equalsIgnoreCase(email.trim()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Order order = new Order();
        order.setClient(client);
        order.setStatus(StatusOrder.PENDING);
        order.setDeliveryType(dto.getDeliveryType());
        order.setDeliveryDate(dto.getDeliveryDate());
        order.setDeliveryFee(calculateDeliveryFee(dto.getDeliveryType()));
        order.setDateCreate(LocalDateTime.now());
        order.setTotal(BigDecimal.ZERO);

        order = orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    private BigDecimal calculateDeliveryFee(DeliveryType deliveryType) {
        if (deliveryType == DeliveryType.ENTREGA) {
            return BigDecimal.valueOf(deliveryFeeValue);
        }
        return BigDecimal.ZERO;
    }

    @Transactional
    public void updateTotal(Long orderId) {
        Order order = orderRepository.findByIdWithDetails(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        order.setTotal(calculateTotal(order));
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> findAll() {
        return orderRepository.findAllWithDetails()
                .stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponseDto findById(Long id) {
        Order order = orderRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        return new OrderResponseDto(order);
    }

    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        orderRepository.delete(order);
    }

    @Transactional
    public OrderResponseDto updateDeliveryType(Long orderId, DeliveryType newDeliveryType) {
        Order order = orderRepository.findByIdWithDetails(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        order.setDeliveryType(newDeliveryType);
        order.setDeliveryFee(calculateDeliveryFee(newDeliveryType));
        order.setTotal(calculateTotal(order));

        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    public Order findEntityById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }

    private BigDecimal calculateTotal(Order order) {
        BigDecimal itemsTotal = order.getItems().stream()
                .map(item -> item.getTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return itemsTotal.add(order.getDeliveryFee());
    }
}

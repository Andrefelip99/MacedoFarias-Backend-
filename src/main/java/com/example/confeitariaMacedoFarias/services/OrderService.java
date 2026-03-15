package com.example.confeitariaMacedoFarias.services;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.confeitariaMacedoFarias.dto.OrderInsertDto;
import com.example.confeitariaMacedoFarias.dto.OrderResponseDto;
import com.example.confeitariaMacedoFarias.entities.Client;
import com.example.confeitariaMacedoFarias.entities.DeliveryType;
import com.example.confeitariaMacedoFarias.entities.Order;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.ClientRepository;
import com.example.confeitariaMacedoFarias.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    private static final BigDecimal DELIVERY_FEE = BigDecimal.valueOf(6.0);

    @Transactional
    public OrderResponseDto insert(OrderInsertDto dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Order order = new Order();
        order.setClient(client);
        order.setStatus(dto.getStatus());
        order.setDeliveryType(dto.getDeliveryType());
        order.setDeliveryFee(calculateDeliveryFee(dto.getDeliveryType()));
        order.setDateCreate(LocalDateTime.now());
        order.setTotal(BigDecimal.ZERO); // será calculado depois de adicionar itens
        order = orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    private BigDecimal calculateDeliveryFee(DeliveryType deliveryType) {
        return deliveryType == DeliveryType.ENTREGA ? DELIVERY_FEE : BigDecimal.ZERO;
    }

    @Transactional
    public void updateTotal(Long orderId) {
        Order order = orderRepository.findByIdWithDetails(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        BigDecimal itemsTotal = order.getItems().stream()
                .map(item -> item.getTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(itemsTotal.add(order.getDeliveryFee()));
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
        updateTotal(orderId);
        return new OrderResponseDto(order);
    }

    public Order findEntityById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }

}

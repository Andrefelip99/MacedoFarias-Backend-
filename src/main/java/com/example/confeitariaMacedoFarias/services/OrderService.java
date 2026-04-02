package com.example.confeitariaMacedoFarias.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
        Client client = null;

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            System.out.println("EMAIL TOKEN: " + email);
            client = clientRepository.findByEmailIgnoreCase(email).orElse(null);
        }

        if (client == null) {
            if (dto.getClientId() == null) {
                throw new ResourceNotFoundException("Cliente não informado");
            }
            client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        } else if (dto.getClientId() != null && !client.getId().equals(dto.getClientId())) {
            if (!hasRole(authentication, "ROLE_ADMIN")) {
                throw new ResourceNotFoundException("Cliente não corresponde ao usuário autenticado");
            }
            client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        }

        Order order = new Order();
        order.setClient(client);
        order.setStatus(StatusOrder.PENDING);
        order.setDeliveryType(dto.getDeliveryType());
        order.setDeliveryDate(dto.getDeliveryDate());
        order.setDeliveryFee(calculateDeliveryFee(dto.getDeliveryType()));
        order.setDateCreate(LocalDateTime.now());
        order.setTotal(BigDecimal.ZERO);
        applyDeliveryAddress(order, client, dto);

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

    private boolean hasRole(Authentication authentication, String role) {
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> role.equals(auth.getAuthority()));
    }

    private BigDecimal calculateTotal(Order order) {
        BigDecimal itemsTotal = order.getItems().stream()
                .map(item -> item.getTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return itemsTotal.add(order.getDeliveryFee());
    }

    private void applyDeliveryAddress(Order order, Client client, OrderInsertDto dto) {
        String zipCode = firstNonBlank(dto.getZipCode(), client.getZipCode());
        String street = firstNonBlank(dto.getStreet(), client.getStreet());
        String number = firstNonBlank(dto.getNumber(), client.getNumber());
        String complement = firstNonBlank(dto.getComplement(), client.getComplement());
        String neighborhood = firstNonBlank(dto.getNeighborhood(), client.getNeighborhood());
        String city = firstNonBlank(dto.getCity(), client.getCity());
        String state = firstNonBlank(dto.getState(), client.getState());

        if (dto.getDeliveryType() == DeliveryType.ENTREGA) {
            if (isBlank(zipCode) || isBlank(street) || isBlank(number)
                    || isBlank(neighborhood) || isBlank(city) || isBlank(state)) {
                throw new ResourceNotFoundException("Endereco de entrega incompleto");
            }
        }

        order.setDeliveryZipCode(zipCode);
        order.setDeliveryStreet(street);
        order.setDeliveryNumber(number);
        order.setDeliveryComplement(complement);
        order.setDeliveryNeighborhood(neighborhood);
        order.setDeliveryCity(city);
        order.setDeliveryState(state);

        // Atualiza endereco do cliente quando informado no pedido
        if (hasAnyAddress(dto)) {
            client.setZipCode(zipCode);
            client.setStreet(street);
            client.setNumber(number);
            client.setComplement(complement);
            client.setNeighborhood(neighborhood);
            client.setCity(city);
            client.setState(state);
            clientRepository.save(client);
        }
    }

    private boolean hasAnyAddress(OrderInsertDto dto) {
        return !isBlank(dto.getZipCode())
                || !isBlank(dto.getStreet())
                || !isBlank(dto.getNumber())
                || !isBlank(dto.getComplement())
                || !isBlank(dto.getNeighborhood())
                || !isBlank(dto.getCity())
                || !isBlank(dto.getState());
    }

    private String firstNonBlank(String primary, String fallback) {
        if (!isBlank(primary)) {
            return primary;
        }
        return isBlank(fallback) ? null : fallback;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

package com.example.confeitariaMacedoFarias.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.confeitariaMacedoFarias.entities.Order;

import lombok.Getter;

@Getter
public class OrderResponseDto {

    private Long id;
    private Long clientId;
    private String clientName;
    private String status;
    private BigDecimal total;
    private String deliveryType;
    private BigDecimal deliveryFee;
    private LocalDate deliveryDate;
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private List<ItemOrderResponseDto> items;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.clientId = order.getClient().getId();
        this.clientName = order.getClient().getName();
        this.status = order.getStatus().name();
        this.total = order.getTotal();
        this.deliveryType = order.getDeliveryType().name();
        this.deliveryFee = order.getDeliveryFee();
        this.deliveryDate = order.getDeliveryDate();
        this.zipCode = order.getDeliveryZipCode();
        this.street = order.getDeliveryStreet();
        this.number = order.getDeliveryNumber();
        this.complement = order.getDeliveryComplement();
        this.neighborhood = order.getDeliveryNeighborhood();
        this.city = order.getDeliveryCity();
        this.state = order.getDeliveryState();
        this.items = order.getItems() != null
                ? order.getItems().stream().map(ItemOrderResponseDto::new).collect(Collectors.toList())
                : null;
    }
}

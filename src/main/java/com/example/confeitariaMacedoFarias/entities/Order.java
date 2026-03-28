package com.example.confeitariaMacedoFarias.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "Status")
    private StatusOrder status;
    @Column(name = "Total")
    private BigDecimal total;
    @Column(name = "date_create")
    private LocalDateTime dateCreate;
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    @Column(name = "delivery_type")
    @Enumerated(EnumType.ORDINAL)
    private DeliveryType deliveryType;
    @Column(name = "delivery_fee")
    private BigDecimal deliveryFee;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "order")
    private List<ItemOrder> items = new ArrayList<>();

}

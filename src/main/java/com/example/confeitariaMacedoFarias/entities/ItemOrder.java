package com.example.confeitariaMacedoFarias.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;

@Entity
@Table(name = "tb_items_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade")
    private Integer quantity;
    @Column(name = "price_unit")
    private BigDecimal priceUnit;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public BigDecimal getTotal() {
        return priceUnit.multiply(BigDecimal.valueOf(quantity));
    }
}

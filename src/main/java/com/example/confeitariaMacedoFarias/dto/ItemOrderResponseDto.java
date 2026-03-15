package com.example.confeitariaMacedoFarias.dto;

import java.math.BigDecimal;

import com.example.confeitariaMacedoFarias.entities.ItemOrder;
import com.example.confeitariaMacedoFarias.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrderResponseDto {

    private Long id;
    private String productName;
    private Integer quantity;
    private BigDecimal priceUnit;
    private BigDecimal total;

    public ItemOrderResponseDto(ItemOrder entity) {
        this.id = entity.getId();
        Product product = entity.getProduct();
        this.productName = product.getName();
        this.quantity = entity.getQuantity();
        this.priceUnit = entity.getPriceUnit();
        this.total = entity.getTotal();
    }
}

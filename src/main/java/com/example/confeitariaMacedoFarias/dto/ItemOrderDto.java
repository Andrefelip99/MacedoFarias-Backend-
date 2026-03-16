package com.example.confeitariaMacedoFarias.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import com.example.confeitariaMacedoFarias.entities.ItemOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrderDto {
    private Long id;

    @PositiveOrZero(message = "A quantidade deve ser um numero positivo.")
    private Integer quantity;

    @Positive(message = "O preco unitario deve ser um numero positivo.")
    @NotNull(message = "O preco unitario e obrigatorio.")
    private BigDecimal priceUnit;

    public ItemOrderDto(ItemOrder entity) {
        this.id = entity.getId();
        this.quantity = entity.getQuantity();
        this.priceUnit = entity.getPriceUnit();
    }
}

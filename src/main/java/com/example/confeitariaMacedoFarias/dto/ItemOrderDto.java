package com.example.confeitariaMacedoFarias.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrderDto {
    private Long id;
    @PositiveOrZero(message = "A quantidade deve ser um número positivo.")
    private Integer quantity;
    @Positive(message = "O preço unitário deve ser um número positivo.")
    @NotBlank(message = "O preço unitário é obrigatório.")
    private BigDecimal priceUnit;


    public ItemOrderDto(ItemOrderDto entity){
        this.id = entity.getId();
        this.quantity = entity.getQuantity();
        this.priceUnit = entity.getPriceUnit();
        
    }

 

}
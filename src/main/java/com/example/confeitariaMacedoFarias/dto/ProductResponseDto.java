package com.example.confeitariaMacedoFarias.dto;

import java.math.BigDecimal;

import com.example.confeitariaMacedoFarias.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean active;
    private String imageUrl;


    public ProductResponseDto(Product entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.active = entity.isActive();
        this.imageUrl = entity.getImageUrl();
    }

    
}
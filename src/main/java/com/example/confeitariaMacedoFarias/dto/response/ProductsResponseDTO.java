package com.example.confeitariaMacedoFarias.dto.response;
import com.example.confeitariaMacedoFarias.entities.Category;
import com.example.confeitariaMacedoFarias.entities.Products;

public record ProductsResponseDTO(
        long id,
        String title,
        String description,
        String oneImageUrl,
        String twoImageUrl,
        String threeImageUrl,
        String link,
        Category category
) {

    public ProductsResponseDTO(Products entity) {
        this(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getOneImageUrl(),
                entity.getTwoImageUrl(),
                entity.getThreeImageUrl(),
                entity.getLink(),
                entity.getCategory()
        );
    }
}
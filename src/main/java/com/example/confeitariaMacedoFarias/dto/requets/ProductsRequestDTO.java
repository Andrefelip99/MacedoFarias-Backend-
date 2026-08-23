package com.example.confeitariaMacedoFarias.dto.requets;

import java.math.BigDecimal;

import com.example.confeitariaMacedoFarias.entities.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductsRequestDTO(

    @NotBlank(message = "O campo title não pode estar em branco")
    String title,

    @NotBlank(message = "O campo description não pode estar em branco")
    String description,

    @NotBlank(message = "O campo oneImageUrl não pode estar em branco")
    String oneImageUrl,

    String twoImageUrl,

    String threeImageUrl,
    @NotBlank(message = "O campo link não pode estar em branco")
    String link,
    
    @NotNull(message = "O campo category é obrigatório")
    Category category,

    @NotNull(message = "O campo price é obrigatório")
    BigDecimal price

) {}


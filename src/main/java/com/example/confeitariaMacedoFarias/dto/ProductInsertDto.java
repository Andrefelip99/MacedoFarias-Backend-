package com.example.confeitariaMacedoFarias.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInsertDto {

    @NotBlank(message = "O nome do produto Ã© obrigatÃ³rio")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String name;

    @NotBlank(message = "A descriÃ§Ã£o do produto Ã© obrigatÃ³ria")
    @Size(max = 200, message = "A descriÃ§Ã£o deve ter no mÃ¡ximo 200 caracteres")
    private String description;

    @NotNull(message = "O preÃ§o Ã© obrigatÃ³rio")
    private BigDecimal price;

    private boolean active;

    @NotBlank(message = "A URL da imagem Ã© obrigatÃ³ria")
    private String imageUrl;

    private String imageUrl2;

    private String imageUrl3;
}

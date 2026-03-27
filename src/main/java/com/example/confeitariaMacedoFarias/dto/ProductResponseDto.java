package com.example.confeitariaMacedoFarias.dto;

import java.math.BigDecimal;
import java.util.Base64;

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
    private String imageUrl2;
    private String imageUrl3;
    private String imageDataUrl;
    private String imageDataUrl2;
    private String imageDataUrl3;

    public ProductResponseDto(Product entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.active = entity.isActive();
        this.imageUrl = entity.getImageUrl();
        this.imageUrl2 = entity.getImageUrl2();
        this.imageUrl3 = entity.getImageUrl3();
        this.imageDataUrl = toDataUrl(entity.getImageData(), entity.getImageContentType());
        this.imageDataUrl2 = toDataUrl(entity.getImageData2(), entity.getImageContentType2());
        this.imageDataUrl3 = toDataUrl(entity.getImageData3(), entity.getImageContentType3());
    }

    private String toDataUrl(byte[] bytes, String contentType) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String type = (contentType == null || contentType.isBlank()) ? "image/jpeg" : contentType;
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return "data:" + type + ";base64," + base64;
    }
}

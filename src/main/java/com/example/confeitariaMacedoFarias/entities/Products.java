package com.example.confeitariaMacedoFarias.entities;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_produto")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private String OneImageUrl;
    private String TwoImageUrl;
    private String ThreeImageUrl;
    private String link;
    
    @Enumerated(EnumType.STRING)
    private Category category;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Products(Long id, String title, BigDecimal price, String description, String oneImageUrl, String twoImageUrl,
            String threeImageUrl, String link, Category category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        OneImageUrl = oneImageUrl;
        TwoImageUrl = twoImageUrl;
        ThreeImageUrl = threeImageUrl;
        this.link = link;
        this.category = category;
    }

    

    


}

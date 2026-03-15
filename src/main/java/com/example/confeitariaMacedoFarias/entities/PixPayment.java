package com.example.confeitariaMacedoFarias.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PIX")
@Getter
@Setter
@NoArgsConstructor
public class PixPayment extends Payment {

    private String pixKey; // Chave PIX do recebedor

    private String description; // Descrição do pagamento

    public PixPayment(Order order, String pixKey, String description) {
        this.setOrder(order);
        this.setPaymentMethod("PIX");
        this.pixKey = pixKey;
        this.description = description;
    }
}
package com.example.confeitariaMacedoFarias.dto;

import com.example.confeitariaMacedoFarias.entities.DeliveryType;
import com.example.confeitariaMacedoFarias.entities.StatusOrder;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInsertDto {

    @NotNull(message = "O cliente é obrigatório")
    private Long clientId;

    @NotNull(message = "O status do pedido é obrigatório")
    private StatusOrder status;

    @NotNull(message = "O tipo de entrega é obrigatório")
    private DeliveryType deliveryType;
}

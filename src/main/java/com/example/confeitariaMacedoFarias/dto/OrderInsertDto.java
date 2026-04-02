package com.example.confeitariaMacedoFarias.dto;

import com.example.confeitariaMacedoFarias.entities.DeliveryType;
import java.time.LocalDate;
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

    private Long clientId;

    @NotNull(message = "O tipo de entrega e obrigatorio")
    private DeliveryType deliveryType;

    @NotNull(message = "A data de entrega e obrigatoria")
    private LocalDate deliveryDate;

    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
}

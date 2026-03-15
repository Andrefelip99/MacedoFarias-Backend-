package com.example.confeitariaMacedoFarias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponseDTO {

    private String qrCode;

    private String qrCodeBase64;

    private String status;

}

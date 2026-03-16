package com.example.confeitariaMacedoFarias.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.confeitariaMacedoFarias.services.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentService paymentService;

    @Value("${mercadopago.webhook.secret:}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<Void> receiveWebhook(
            @RequestBody Map<String, Object> payload,
            @RequestHeader(value = "x-signature", required = false) String signature) {

        // Validacao basica de assinatura (usar segredo compartilhado)
        if (signature == null || signature.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (webhookSecret == null || webhookSecret.isBlank()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (!signature.equals(webhookSecret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            String action = (String) payload.get("action");
            if ("payment.updated".equals(action)) {
                @SuppressWarnings("unchecked")
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                String paymentId = (String) data.get("id");

                paymentService.updatePaymentStatus(paymentId);
            }
        } catch (Exception e) {
            // Log error
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}

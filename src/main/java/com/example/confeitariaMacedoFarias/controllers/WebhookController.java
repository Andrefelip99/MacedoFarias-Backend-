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
@RequestMapping("/api/v1/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentService paymentService;

    @Value("${mercadopago.webhook.secret:}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<Void> receiveWebhook(
            @RequestBody Map<String, Object> payload,
            @RequestHeader(value = "x-signature", required = false) String signature) {

        // Validacao simples de assinatura (quando configurado)
        if (webhookSecret != null && !webhookSecret.isBlank()) {
            if (signature == null || signature.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if (!signature.equals(webhookSecret)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        try {
            String action = payload.get("action") != null ? payload.get("action").toString() : null;
            String type = payload.get("type") != null ? payload.get("type").toString() : null;

            boolean isPaymentEvent = "payment.updated".equals(action) || "payment".equals(type);
            if (isPaymentEvent) {
                String paymentId = null;
                Object dataObj = payload.get("data");
                if (dataObj instanceof Map) {
                    Object idObj = ((Map<?, ?>) dataObj).get("id");
                    if (idObj != null) {
                        paymentId = idObj.toString();
                    }
                }
                if (paymentId == null && payload.get("id") != null) {
                    paymentId = payload.get("id").toString();
                }

                if (paymentId != null && !paymentId.isBlank()) {
                    paymentService.updatePaymentStatus(paymentId);
                }
            }
        } catch (Exception e) {
            // Log error
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}

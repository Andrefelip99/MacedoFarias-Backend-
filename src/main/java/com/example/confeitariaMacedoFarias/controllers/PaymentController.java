package com.example.confeitariaMacedoFarias.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.confeitariaMacedoFarias.dto.PaymentResponseDTO;
import com.example.confeitariaMacedoFarias.entities.Order;
import com.example.confeitariaMacedoFarias.services.OrderService;
import com.example.confeitariaMacedoFarias.services.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @PostMapping("/{orderId}")
public ResponseEntity<PaymentResponseDTO> createPayment(@PathVariable Long orderId) throws Exception {

    Order order = orderService.findEntityById(orderId);

    PaymentResponseDTO response = paymentService.createPixPayment(order);

    return ResponseEntity.ok(response);
}
}

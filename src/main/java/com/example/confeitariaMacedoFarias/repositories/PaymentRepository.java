package com.example.confeitariaMacedoFarias.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.confeitariaMacedoFarias.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByMercadoPagoPaymentId(String mercadoPagoPaymentId);
}

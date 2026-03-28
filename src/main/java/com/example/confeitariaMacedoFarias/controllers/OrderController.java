package com.example.confeitariaMacedoFarias.controllers;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.confeitariaMacedoFarias.dto.OrderInsertDto;
import com.example.confeitariaMacedoFarias.dto.OrderResponseDto;
import com.example.confeitariaMacedoFarias.entities.DeliveryType;
import com.example.confeitariaMacedoFarias.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")//
public class OrderController {
    private final OrderService service;

    /**
     * Criar pedido - USER/ADMIN podem criar pedidos
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto insert(@RequestBody OrderInsertDto dto) {
        return service.insert(dto);
    }

    /**
     * Buscar pedido por id - USER/ADMIN
     */
    @GetMapping("/{id}")
    public OrderResponseDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Atualizar tipo de entrega do pedido - USER/ADMIN
     */
    @PutMapping("/{id}/delivery-type")
    public OrderResponseDto updateDeliveryType(@PathVariable Long id, @RequestBody DeliveryType deliveryType) {
        return service.updateDeliveryType(id, deliveryType);
    }

    /**
     * Listar todos os pedidos - USER/ADMIN
     */
    @GetMapping
    public List<OrderResponseDto> findAll() {
        return service.findAll();
    }
}

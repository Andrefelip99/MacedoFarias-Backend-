package com.example.confeitariaMacedoFarias.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.confeitariaMacedoFarias.dto.ItemOrderInsertDto;
import com.example.confeitariaMacedoFarias.dto.ItemOrderResponseDto;
import com.example.confeitariaMacedoFarias.services.ItemOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders/{orderId}/items")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ItemOrderController {

    private final ItemOrderService service;

    /**
     * Adicionar item ao pedido - USER/ADMIN
     */
    @PostMapping
    public ItemOrderResponseDto insert(
            @PathVariable Long orderId,
            @Valid @RequestBody ItemOrderInsertDto dto) {
        return service.insert(orderId, dto);
    }

    /**
     * Listar itens de um pedido - USER/ADMIN
     */
    @GetMapping
    public List<ItemOrderResponseDto> findAll(@PathVariable Long orderId) {
        return service.findByOrderId(orderId);
    }

    /**
     * Remover item do pedido - USER/ADMIN
     */
    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long orderId, @PathVariable Long itemId) {
        service.delete(orderId, itemId);
    }
}

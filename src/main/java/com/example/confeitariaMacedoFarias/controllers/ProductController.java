package com.example.confeitariaMacedoFarias.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.confeitariaMacedoFarias.dto.ProductInsertDto;
import com.example.confeitariaMacedoFarias.dto.ProductResponseDto;
import com.example.confeitariaMacedoFarias.services.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    /**
     * Listar produtos - público
     */
    @GetMapping
    public Page<ProductResponseDto> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    /**
     * Buscar produto por id - público
     */
    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Criar produto - apenas ADMIN
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto insert(@Valid @RequestBody ProductInsertDto dto) {
        return service.insert(dto);
    }

    /**
     * Atualizar produto - apenas ADMIN
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto update(@PathVariable Long id,
            @Valid @RequestBody ProductInsertDto dto) {
        return service.update(id, dto);
    }

    /**
     * Deletar produto - apenas ADMIN
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
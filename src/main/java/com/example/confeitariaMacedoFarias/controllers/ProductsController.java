package com.example.confeitariaMacedoFarias.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.confeitariaMacedoFarias.dto.requets.ProductsRequestDTO;
import com.example.confeitariaMacedoFarias.dto.response.ProductsResponseDTO;
import com.example.confeitariaMacedoFarias.services.ProductsService;



@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductsResponseDTO>> findAll() {
        return ResponseEntity.ok(productsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductsResponseDTO> insert(
            @RequestBody ProductsRequestDTO dto) {

        return ResponseEntity.ok(
                productsService.insert(dto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ProductsRequestDTO dto) {

        return ResponseEntity.ok(
                productsService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        productsService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
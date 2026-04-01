package com.example.confeitariaMacedoFarias.controllers;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
     * Listar produtos - pÃºblico
     */
    @GetMapping
    public Page<ProductResponseDto> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    /**
     * Buscar produto por id - pÃºblico
     */
    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Criar produto - apenas ADMIN (JSON)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto insert(@Valid @RequestBody ProductInsertDto dto) {
        return service.insert(dto);
    }

    /**
     * Criar produto - apenas ADMIN (Multipart com imagens)
     */
    @PostMapping(value = "/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto insertMultipart(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam(defaultValue = "true") boolean active,
            @RequestPart("image1") MultipartFile image1,
            @RequestPart(value = "image2", required = false) MultipartFile image2,
            @RequestPart(value = "image3", required = false) MultipartFile image3) {
        if (image1 == null || image1.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Imagem principal obrigatoria");
        }
        return service.insertMultipart(name, description, price, active, image1, image2, image3);
    }

    /**
     * Atualizar produto - apenas ADMIN (JSON)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto update(@PathVariable Long id,
            @Valid @RequestBody ProductInsertDto dto) {
        return service.update(id, dto);
    }

    /**
     * Atualizar produto - apenas ADMIN (Multipart com imagens)
     */
    @PutMapping(value = "/{id}/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto updateMultipart(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam(defaultValue = "true") boolean active,
            @RequestPart(value = "image1", required = false) MultipartFile image1,
            @RequestPart(value = "image2", required = false) MultipartFile image2,
            @RequestPart(value = "image3", required = false) MultipartFile image3) {
        return service.updateMultipart(id, name, description, price, active, image1, image2, image3);
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

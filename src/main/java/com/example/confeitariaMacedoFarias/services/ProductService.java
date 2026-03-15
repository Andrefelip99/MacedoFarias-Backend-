package com.example.confeitariaMacedoFarias.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.confeitariaMacedoFarias.dto.ProductInsertDto;
import com.example.confeitariaMacedoFarias.dto.ProductResponseDto;
import com.example.confeitariaMacedoFarias.entities.Product;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAll(Pageable pageable) {
        Page<Product> result = repository.findAllWithCategories(pageable);
        return result.map(ProductResponseDto::new);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id) {
        Product product = repository.findByIdWithCategories(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado"));
        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto insert(ProductInsertDto dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductResponseDto(entity);
    }

    @Transactional
    public ProductResponseDto update(Long id, ProductInsertDto dto) {
        Product entity = repository.findByIdWithCategories(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado"));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductResponseDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        Product product = repository.findByIdWithCategories(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado"));
        repository.delete(product);
    }

    private void copyDtoToEntity(ProductInsertDto dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setActive(dto.isActive());
        entity.setImageUrl(dto.getImageUrl());
    }
}

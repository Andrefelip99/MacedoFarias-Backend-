package com.example.confeitariaMacedoFarias.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.confeitariaMacedoFarias.dto.requets.ProductsRequestDTO;
import com.example.confeitariaMacedoFarias.dto.response.ProductsResponseDTO;
import com.example.confeitariaMacedoFarias.entities.Category;
import com.example.confeitariaMacedoFarias.entities.Products;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.ProductsRepository;
import com.example.confeitariaMacedoFarias.services.exepitions.BusinessException;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService;

    @Test
    void findAllShouldMapProducts() {
        Products product = product(1L);
        when(productsRepository.findAll()).thenReturn(List.of(product));

        List<ProductsResponseDTO> result = productsService.findAll();

        assertEquals(1, result.size());
        assertEquals(product.getTitle(), result.get(0).title());
        assertEquals(product.getOneImageUrl(), result.get(0).oneImageUrl());
    }

    @Test
    void findByIdShouldReturnProduct() {
        Products product = product(1L);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductsResponseDTO result = productsService.findById(1L);

        assertEquals(1L, result.id());
        assertEquals(product.getLink(), result.link());
    }

    @Test
    void findByIdShouldThrowWhenProductDoesNotExist() {
        when(productsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productsService.findById(1L));
    }

    @Test
    void insertShouldSaveAndReturnProduct() {
        ProductsRequestDTO request = validRequest();
        Products saved = product(1L);
        when(productsRepository.save(org.mockito.ArgumentMatchers.any(Products.class))).thenReturn(saved);

        ProductsResponseDTO result = productsService.insert(request);

        assertEquals(saved.getId(), result.id());
        assertEquals(request.category(), result.category());
        verify(productsRepository).save(org.mockito.ArgumentMatchers.argThat(entity ->
                request.title().equals(entity.getTitle())
                        && request.oneImageUrl().equals(entity.getOneImageUrl())
                && request.link().equals(entity.getLink())
                && request.category().equals(entity.getCategory())));
    }

    @Test
    void updateShouldChangeAndSaveProduct() {
        ProductsRequestDTO request = validRequest();
        Products product = product(1L);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productsRepository.save(product)).thenReturn(product);

        ProductsResponseDTO result = productsService.update(1L, request);

        assertEquals(request.description(), product.getDescription());
        assertEquals(request.threeImageUrl(), product.getThreeImageUrl());
        assertEquals(request.category(), product.getCategory());
        assertEquals(product.getId(), result.id());
        verify(productsRepository).save(product);
    }

    @Test
    void deleteShouldRemoveExistingProduct() {
        Products product = product(1L);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));

        productsService.delete(1L);

        verify(productsRepository).delete(product);
    }

    @Test
    void deleteShouldThrowWhenProductDoesNotExist() {
        when(productsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productsService.delete(1L));
        verify(productsRepository).findById(1L);
        verifyNoMoreInteractions(productsRepository);
    }

    @Test
    void insertShouldRejectMissingRequiredFields() {
        assertThrows(BusinessException.class, () -> productsService.insert(null));
        assertThrows(BusinessException.class, () -> productsService.insert(
                new ProductsRequestDTO("", "description", "image", null, null, "link", null, BigDecimal.valueOf(20.0))));
        assertThrows(BusinessException.class, () -> productsService.insert(
                new ProductsRequestDTO("title", "", "image", null, null, "link", null, BigDecimal.valueOf(20.0))));
        assertThrows(BusinessException.class, () -> productsService.insert(
                new ProductsRequestDTO("title", "description", "", null, null, "link", null, BigDecimal.valueOf(20.0))));
        assertThrows(BusinessException.class, () -> productsService.insert(
                new ProductsRequestDTO("title", "description", "image", null, null, "", null, BigDecimal.valueOf(20.0))));
        assertThrows(BusinessException.class, () -> productsService.insert(
            new ProductsRequestDTO("title", "description", "image", null, null, "link", null, BigDecimal.valueOf(20.0))));
        verifyNoInteractions(productsRepository);
    }

    @Test
    void updateShouldValidateBeforeLoadingProduct() {
        assertThrows(BusinessException.class, () -> productsService.update(1L,
                new ProductsRequestDTO(null, "description", "image", null, null, "link", null, BigDecimal.valueOf(20.0))));

        verifyNoInteractions(productsRepository);
    }

    private ProductsRequestDTO validRequest() {
        return new ProductsRequestDTO(
                "Bolo de chocolate",
                "Bolo com cobertura",
                "https://example.com/one.jpg",
                "https://example.com/two.jpg",
                "https://example.com/three.jpg",
                "https://example.com/bolo",
                Category.Bolos, BigDecimal.valueOf(20.0));
    }

    private Products product(Long id) {
    return new Products(
            id,
            "Bolo de chocolate",
            BigDecimal.valueOf(20.0),
            "Bolo com cobertura",
            "https://example.com/one.jpg",
            "https://example.com/two.jpg",
            "https://example.com/three.jpg",
            "https://example.com/bolo",
            Category.Bolos
    );
}
}

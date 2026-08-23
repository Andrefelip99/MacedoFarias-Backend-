package com.example.confeitariaMacedoFarias.services;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.confeitariaMacedoFarias.dto.requets.ProductsRequestDTO;
import com.example.confeitariaMacedoFarias.dto.response.ProductsResponseDTO;
import com.example.confeitariaMacedoFarias.entities.Products;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.ProductsRepository;
import com.example.confeitariaMacedoFarias.services.exepitions.BusinessException;


@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<ProductsResponseDTO> findAll() {

        List<Products> list = productsRepository.findAll();

        return list.stream()
                .map(ProductsResponseDTO::new)
                .toList();
    }

    public ProductsResponseDTO findById(Long id) {
        return new ProductsResponseDTO(findProjectById(id));
    }

    public ProductsResponseDTO insert(ProductsRequestDTO dto) {

        validateProducts(dto);

        Products entity = new Products();

        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setOneImageUrl(dto.oneImageUrl());
        entity.setTwoImageUrl(dto.twoImageUrl());
        entity.setThreeImageUrl(dto.threeImageUrl());
        entity.setLink(dto.link());
        entity.setCategory(dto.category());
        entity.setPrice(dto.price());

        entity = productsRepository.save(entity);

        return new ProductsResponseDTO(entity);
    }

    public ProductsResponseDTO update(Long id, ProductsRequestDTO dto) {

        validateProducts(dto);

        Products entity = findProjectById(id);

        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setOneImageUrl(dto.oneImageUrl());
        entity.setTwoImageUrl(dto.twoImageUrl());
        entity.setThreeImageUrl(dto.threeImageUrl());
        entity.setLink(dto.link());
        entity.setCategory(dto.category());
        entity.setPrice(dto.price());
        entity = productsRepository.save(entity);

        return new ProductsResponseDTO(entity);
    }

    public void delete(Long id) {

        Products entity = findProjectById(id);

        productsRepository.delete(entity);
    }

    private Products findProjectById(Long id) {

        return productsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Projeto não encontrado."));
    }

    private void validateProducts(ProductsRequestDTO dto) {

        if (dto == null) {
            throw new BusinessException("Os dados do projeto são obrigatórios.");
        }

        if (dto.title() == null || dto.title().isBlank()) {
            throw new BusinessException("O título do projeto é obrigatório.");
        }

        if (dto.description() == null || dto.description().isBlank()) {
            throw new BusinessException("A descrição do projeto é obrigatória.");
        }

        if (dto.oneImageUrl() == null || dto.oneImageUrl().isBlank()) {
            throw new BusinessException("A URL da imagem é obrigatória.");
        }

        if (dto.link() == null || dto.link().isBlank()) {
            throw new BusinessException("O link do projeto é obrigatório.");
        }

        if (dto.category() == null) {
            throw new BusinessException("A categoria do projeto é obrigatória.");
        }
    }
}

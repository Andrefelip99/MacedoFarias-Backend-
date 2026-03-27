package com.example.confeitariaMacedoFarias.services;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.imageio.ImageIO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " nÃ£o encontrado"));
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
    public ProductResponseDto insertMultipart(String name, String description, BigDecimal price,
            boolean active, MultipartFile image1, MultipartFile image2, MultipartFile image3) {
        Product entity = new Product();
        applyBasics(entity, name, description, price, active);
        applyImage(entity, image1, 1);
        applyImage(entity, image2, 2);
        applyImage(entity, image3, 3);
        entity = repository.save(entity);
        return new ProductResponseDto(entity);
    }

    @Transactional
    public ProductResponseDto update(Long id, ProductInsertDto dto) {
        Product entity = repository.findByIdWithCategories(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " nÃ£o encontrado"));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductResponseDto(entity);
    }

    @Transactional
    public ProductResponseDto updateMultipart(Long id, String name, String description, BigDecimal price,
            boolean active, MultipartFile image1, MultipartFile image2, MultipartFile image3) {
        Product entity = repository.findByIdWithCategories(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " nÃ£o encontrado"));
        applyBasics(entity, name, description, price, active);
        applyImage(entity, image1, 1);
        applyImage(entity, image2, 2);
        applyImage(entity, image3, 3);
        entity = repository.save(entity);
        return new ProductResponseDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        Product product = repository.findByIdWithCategories(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " nÃ£o encontrado"));
        repository.delete(product);
    }

    private void copyDtoToEntity(ProductInsertDto dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setActive(dto.isActive());
        entity.setImageUrl(dto.getImageUrl());
        entity.setImageUrl2(dto.getImageUrl2());
        entity.setImageUrl3(dto.getImageUrl3());
    }

    private void applyBasics(Product entity, String name, String description, BigDecimal price, boolean active) {
        entity.setName(name);
        entity.setDescription(description);
        entity.setPrice(price);
        entity.setActive(active);
    }

    private void applyImage(Product entity, MultipartFile file, int slot) {
        if (file == null || file.isEmpty()) {
            return;
        }
        try {
            ImageResult result = normalizeImage(file);
            byte[] bytes = result.bytes;
            String contentType = result.contentType;
            if (slot == 1) {
                entity.setImageData(bytes);
                entity.setImageContentType(contentType);
            } else if (slot == 2) {
                entity.setImageData2(bytes);
                entity.setImageContentType2(contentType);
            } else if (slot == 3) {
                entity.setImageData3(bytes);
                entity.setImageContentType3(contentType);
            }
        } catch (IOException e) {
            throw new RuntimeException("Nao foi possivel processar a imagem");
        }
    }

    private ImageResult normalizeImage(MultipartFile file) throws IOException {
        try (InputStream input = file.getInputStream()) {
            BufferedImage original = ImageIO.read(input);
            if (original == null) {
                throw new IOException("Formato de imagem invalido");
            }

            int width = original.getWidth();
            int height = original.getHeight();
            int size = Math.min(width, height);
            int x = (width - size) / 2;
            int y = (height - size) / 2;
            BufferedImage square = original.getSubimage(x, y, size, size);

            int target = 1200;
            BufferedImage scaled = new BufferedImage(target, target, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = scaled.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(square, 0, 0, target, target, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(scaled, "jpg", baos);
            return new ImageResult(baos.toByteArray(), "image/jpeg");
        }
    }

    private static class ImageResult {
        private final byte[] bytes;
        private final String contentType;

        private ImageResult(byte[] bytes, String contentType) {
            this.bytes = bytes;
            this.contentType = contentType;
        }
    }
}

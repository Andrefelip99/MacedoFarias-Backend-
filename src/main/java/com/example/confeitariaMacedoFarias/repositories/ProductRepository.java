package com.example.confeitariaMacedoFarias.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.confeitariaMacedoFarias.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @EntityGraph(attributePaths = "categories")
    @Query("SELECT p FROM Product p")
    Page<Product> findAllWithCategories(Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    @Query("SELECT p FROM Product p WHERE p.active = true")
    Page<Product> findAllActiveWithCategories(Pageable pageable);
    
    @EntityGraph(attributePaths = "categories")
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdWithCategories(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM tb_product_category WHERE product_id = :productId", nativeQuery = true)
    void deleteProductCategories(@Param("productId") Long productId);
}

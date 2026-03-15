package com.example.confeitariaMacedoFarias.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.confeitariaMacedoFarias.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT DISTINCT p FROM Product p "
         + "LEFT JOIN FETCH p.categories")
    Page<Product> findAllWithCategories(Pageable pageable);
    
    @Query("SELECT p FROM Product p "
         + "LEFT JOIN FETCH p.categories "
         + "WHERE p.id = :id")
    Optional<Product> findByIdWithCategories(@Param("id") Long id);
}
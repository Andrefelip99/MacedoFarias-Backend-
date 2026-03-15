package com.example.confeitariaMacedoFarias.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.confeitariaMacedoFarias.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
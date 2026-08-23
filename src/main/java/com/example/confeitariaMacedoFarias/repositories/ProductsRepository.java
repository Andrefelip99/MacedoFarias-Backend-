package com.example.confeitariaMacedoFarias.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.confeitariaMacedoFarias.entities.Products;


@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

}
package com.example.confeitariaMacedoFarias.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.confeitariaMacedoFarias.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query("SELECT DISTINCT o FROM Order o "
         + "LEFT JOIN FETCH o.client "
         + "LEFT JOIN FETCH o.items i "
         + "LEFT JOIN FETCH i.product")
    List<Order> findAllWithDetails();
    
    @Query("SELECT o FROM Order o "
         + "LEFT JOIN FETCH o.client "
         + "LEFT JOIN FETCH o.items i "
         + "LEFT JOIN FETCH i.product "
         + "WHERE o.id = :id")
    Optional<Order> findByIdWithDetails(@Param("id") Long id);
}

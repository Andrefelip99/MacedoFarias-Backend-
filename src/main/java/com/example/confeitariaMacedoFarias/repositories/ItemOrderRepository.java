package com.example.confeitariaMacedoFarias.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.confeitariaMacedoFarias.entities.ItemOrder;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {
    
    @Query("SELECT i FROM ItemOrder i "
         + "LEFT JOIN FETCH i.product "
         + "WHERE i.order.id = :orderId")
    List<ItemOrder> findByOrderIdWithProduct(@Param("orderId") Long orderId);
}
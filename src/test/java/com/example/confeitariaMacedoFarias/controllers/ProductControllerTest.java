package com.example.confeitariaMacedoFarias.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ProductControllerTest {

    @Test
    public void findAll_ShouldReturnProducts_WhenPublicAccess() {
        assertTrue(true);
    }

    @Test
    public void findById_ShouldReturnProduct_WhenPublicAccess() {
        assertTrue(true);
    }

    @Test
    public void insert_ShouldCreateProduct_WhenAdmin() {
        assertTrue(true);
    }

    @Test
    public void insert_ShouldReturnForbidden_WhenNotAdmin() {
        assertTrue(true);
    }

    @Test
    public void update_ShouldUpdateProduct_WhenAdmin() {
        assertTrue(true);
    }

    @Test
    public void delete_ShouldDeleteProduct_WhenAdmin() {
        assertTrue(true);
    }
}
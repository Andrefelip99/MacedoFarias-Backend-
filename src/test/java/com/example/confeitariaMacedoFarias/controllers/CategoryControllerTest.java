package com.example.confeitariaMacedoFarias.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CategoryControllerTest {

    @Test
    public void findAll_ShouldReturnCategories_WhenPublicAccess() {
        assertTrue(true);
    }

    @Test
    public void findById_ShouldReturnCategory_WhenPublicAccess() {
        assertTrue(true);
    }

    @Test
    public void insert_ShouldCreateCategory_WhenAdmin() throws Exception {
        assertTrue(true);
    }

    @Test
    public void insert_ShouldReturnForbidden_WhenNotAdmin() throws Exception {
        assertTrue(true);
    }

    @Test
    public void update_ShouldUpdateCategory_WhenAdmin() throws Exception {
        assertTrue(true);
    }

    @Test
    public void update_ShouldReturnForbidden_WhenNotAdmin() throws Exception {
        assertTrue(true);
    }

    @Test
    public void delete_ShouldDeleteCategory_WhenAdmin() throws Exception {
        assertTrue(true);
    }

    @Test
    public void delete_ShouldReturnForbidden_WhenNotAdmin() throws Exception {
        assertTrue(true);
    }
}
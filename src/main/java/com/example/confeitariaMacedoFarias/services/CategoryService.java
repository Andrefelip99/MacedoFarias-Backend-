package com.example.confeitariaMacedoFarias.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.confeitariaMacedoFarias.entities.Category;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    @Transactional
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria com id " + id + " não encontrada"));
    }

    @Transactional
    public Category insert(Category category) {
        return repository.save(category);
    }

    @Transactional
    public Category update(Long id, Category category) {
        Category entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria com id " + id + " não encontrada"));
        entity.setName(category.getName());
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria com id " + id + " não encontrada"));
        repository.delete(category);
    }
}
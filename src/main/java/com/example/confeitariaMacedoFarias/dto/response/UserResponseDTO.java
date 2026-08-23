package com.example.confeitariaMacedoFarias.dto.response;

import com.example.confeitariaMacedoFarias.entities.User;

public record UserResponseDTO(
        long id,
        String nome,
        String email) {

    public UserResponseDTO(User entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getEmail());
    }
}
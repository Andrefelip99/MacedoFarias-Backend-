package com.example.confeitariaMacedoFarias.dto.requets;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "O campo email não pode estar em branco") @Email(message = "O campo email deve ser um email válido") String email,
        @NotBlank(message = "O campo senha não pode estar em branco") @Size(min = 4, message = "O campo senha deve ter no mínimo 4 caracteres") String password) {
}
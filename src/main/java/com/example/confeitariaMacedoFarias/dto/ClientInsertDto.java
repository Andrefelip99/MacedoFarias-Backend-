package com.example.confeitariaMacedoFarias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientInsertDto {

    @Size(min = 2, max = 20, message = "O nome deve conter entre 2 e 20 caracteres.")
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @Size(min = 5, max = 50, message = "O email deve conter entre 5 e 50 caracteres.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @Size(min = 9, max = 15, message = "O número de telefone deve conter entre 9 e 15 caracteres.")
    @NotBlank(message = "O número de telefone é obrigatório.")
    private String phoneNumber;

    @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres.")
    @NotBlank(message = "A senha é obrigatória.")
    private String password;
}

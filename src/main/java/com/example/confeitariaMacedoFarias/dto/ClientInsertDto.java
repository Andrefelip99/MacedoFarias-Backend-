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
    @NotBlank(message = "O nome e obrigatorio.")
    private String name;

    @Size(min = 5, max = 50, message = "O email deve conter entre 5 e 50 caracteres.")
    @NotBlank(message = "O email e obrigatorio.")
    private String email;

    @Size(min = 9, max = 15, message = "O numero de telefone deve conter entre 9 e 15 caracteres.")
    @NotBlank(message = "O numero de telefone e obrigatorio.")
    private String phoneNumber;

    @Size(min = 8, max = 9, message = "O CEP deve conter 8 caracteres.")
    @NotBlank(message = "O CEP e obrigatorio.")
    private String zipCode;

    @Size(min = 2, max = 80, message = "O logradouro deve conter entre 2 e 80 caracteres.")
    @NotBlank(message = "O logradouro e obrigatorio.")
    private String street;

    @Size(min = 1, max = 10, message = "O numero deve conter entre 1 e 10 caracteres.")
    @NotBlank(message = "O numero e obrigatorio.")
    private String number;

    @Size(max = 40, message = "O complemento deve ter no maximo 40 caracteres.")
    private String complement;

    @Size(min = 2, max = 60, message = "O bairro deve conter entre 2 e 60 caracteres.")
    @NotBlank(message = "O bairro e obrigatorio.")
    private String neighborhood;

    @Size(min = 2, max = 60, message = "A cidade deve conter entre 2 e 60 caracteres.")
    @NotBlank(message = "A cidade e obrigatoria.")
    private String city;

    @Size(min = 2, max = 2, message = "O estado deve conter 2 caracteres.")
    @NotBlank(message = "O estado e obrigatorio.")
    private String state;

    @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres.")
    @NotBlank(message = "A senha e obrigatoria.")
    private String password;
}

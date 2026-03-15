package com.example.confeitariaMacedoFarias.dto;

import com.example.confeitariaMacedoFarias.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInsertDto {

    private String email;
    private String password;

    public UserInsertDto(User entity) {

        this.email = entity.getEmail();
        this.password = entity.getPassword();
    }
}
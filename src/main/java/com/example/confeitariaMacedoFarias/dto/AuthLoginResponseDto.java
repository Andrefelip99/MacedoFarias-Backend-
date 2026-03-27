package com.example.confeitariaMacedoFarias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthLoginResponseDto {
    private String token;
    private AuthUserDto user;
}

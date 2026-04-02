package com.example.confeitariaMacedoFarias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUserDto {
    private Long id;
    private String email;
    private String role;
    private String name;
    private String phoneNumber;
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
}

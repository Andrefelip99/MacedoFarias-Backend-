package com.example.confeitariaMacedoFarias.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.confeitariaMacedoFarias.dto.AuthLoginResponseDto;
import com.example.confeitariaMacedoFarias.dto.LoginDto;
import com.example.confeitariaMacedoFarias.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public AuthLoginResponseDto login(@Valid @RequestBody LoginDto login) {
        return service.login(login.getEmail(), login.getPassword());
    }
}

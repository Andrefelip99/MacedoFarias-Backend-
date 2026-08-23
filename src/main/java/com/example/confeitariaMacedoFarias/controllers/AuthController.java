package com.example.confeitariaMacedoFarias.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.confeitariaMacedoFarias.dto.LoginResponseDTO;
import com.example.confeitariaMacedoFarias.dto.requets.UserRequestDTO;
import com.example.confeitariaMacedoFarias.services.AuthService;



@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody UserRequestDTO dto) {

        return ResponseEntity.ok(
                authService.login(dto));
    }
}
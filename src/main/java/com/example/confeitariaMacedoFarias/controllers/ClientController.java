package com.example.confeitariaMacedoFarias.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.confeitariaMacedoFarias.dto.ClientInsertDto;
import com.example.confeitariaMacedoFarias.dto.ClientResponseDto;
import com.example.confeitariaMacedoFarias.dto.LoginDto;
import com.example.confeitariaMacedoFarias.services.ClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    /**
     * Registro de novo cliente - público
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDto register(@Valid @RequestBody ClientInsertDto dto) {
        return service.insert(dto);
    }

    /**
     * Login de cliente - público
     */
    @PostMapping("/login")
    public ClientResponseDto login(@Valid @RequestBody LoginDto login) {
        return service.login(login.getEmail(), login.getPassword());
    }

    /**
     * Buscar dados do cliente - apenas cliente autenticado (USER/ADMIN)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ClientResponseDto findById(@PathVariable Long id) {
        return service.findById(id);
    }
}

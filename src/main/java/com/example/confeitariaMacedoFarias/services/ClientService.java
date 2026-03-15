package com.example.confeitariaMacedoFarias.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.confeitariaMacedoFarias.dto.ClientInsertDto;
import com.example.confeitariaMacedoFarias.dto.ClientResponseDto;
import com.example.confeitariaMacedoFarias.entities.Client;
import com.example.confeitariaMacedoFarias.exceptions.DatabaseException;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.ClientRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.password:securePassword123}")
    private String adminPassword;

     @PostConstruct
    public void initAdmin() {
        String adminEmail = "macedofarias@gmail.com";
        if (repository.findByEmail(adminEmail).isEmpty()) {
            Client admin = new Client();
            admin.setName("Bruna");
            admin.setEmail(adminEmail);
            admin.setPhoneNumber("21 98259-1226");
            admin.setPassword(passwordEncoder.encode(adminPassword));
            repository.save(admin);
        }
    }

    @Transactional
    public ClientResponseDto insert(ClientInsertDto dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DatabaseException("Email já cadastrado");
        }
        Client client = new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setPassword(passwordEncoder.encode(dto.getPassword()));
        client = repository.save(client);
        return new ClientResponseDto(client);
    }

    @Transactional(readOnly = true)
    public ClientResponseDto login(String email, String password) {
        Client client = repository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Email ou senha inválidos"));
        
        if (!passwordEncoder.matches(password, client.getPassword())) {
            throw new ResourceNotFoundException("Email ou senha inválidos");
        }
        return new ClientResponseDto(client);
    }

    @Transactional(readOnly = true)
    public ClientResponseDto findById(Long id) {
        Client client = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente com id " + id + " não encontrado"));
        return new ClientResponseDto(client);
    }
}

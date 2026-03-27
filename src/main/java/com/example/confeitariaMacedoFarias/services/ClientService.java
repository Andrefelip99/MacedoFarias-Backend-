package com.example.confeitariaMacedoFarias.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.confeitariaMacedoFarias.dto.ClientInsertDto;
import com.example.confeitariaMacedoFarias.dto.ClientResponseDto;
import com.example.confeitariaMacedoFarias.dto.LoginResponseDto;
import com.example.confeitariaMacedoFarias.entities.Client;
import com.example.confeitariaMacedoFarias.exceptions.DatabaseException;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.ClientRepository;
import com.example.confeitariaMacedoFarias.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Transactional
    public ClientResponseDto insert(ClientInsertDto dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DatabaseException("Email ja cadastrado");
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
    public LoginResponseDto login(String email, String password) {
        Client client = repository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Email ou senha invalidos"));
        
        if (!passwordEncoder.matches(password, client.getPassword())) {
            throw new ResourceNotFoundException("Email ou senha invalidos");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtService.generateToken(userDetails);
        return new LoginResponseDto(token, new ClientResponseDto(client));
    }

    @Transactional(readOnly = true)
    public ClientResponseDto findById(Long id) {
        Client client = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente com id " + id + " nao encontrado"));
        return new ClientResponseDto(client);
    }
}

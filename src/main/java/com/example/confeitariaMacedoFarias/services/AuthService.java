package com.example.confeitariaMacedoFarias.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.confeitariaMacedoFarias.dto.AuthLoginResponseDto;
import com.example.confeitariaMacedoFarias.dto.AuthUserDto;
import com.example.confeitariaMacedoFarias.entities.Client;
import com.example.confeitariaMacedoFarias.entities.User;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.ClientRepository;
import com.example.confeitariaMacedoFarias.repositories.UserRepository;
import com.example.confeitariaMacedoFarias.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Transactional(readOnly = true)
    public AuthLoginResponseDto login(String email, String password) {
        User admin = userRepository.findByEmail(email).orElse(null);
        if (admin != null) {
            if (!passwordEncoder.matches(password, admin.getPassword())) {
                throw new ResourceNotFoundException("Email ou senha invalidos");
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String token = jwtService.generateToken(userDetails);
            AuthUserDto user = new AuthUserDto(
                admin.getId(),
                admin.getEmail(),
                admin.getRole() != null ? admin.getRole().name() : "ADMIN",
                null,
                null
            );
            return new AuthLoginResponseDto(token, user);
        }

        Client client = clientRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Email ou senha invalidos"));

        if (!passwordEncoder.matches(password, client.getPassword())) {
            throw new ResourceNotFoundException("Email ou senha invalidos");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtService.generateToken(userDetails);
        AuthUserDto user = new AuthUserDto(
            client.getId(),
            client.getEmail(),
            "USER",
            client.getName(),
            client.getPhoneNumber()
        );
        return new AuthLoginResponseDto(token, user);
    }
}

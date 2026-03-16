package com.example.confeitariaMacedoFarias.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.confeitariaMacedoFarias.entities.Role;
import com.example.confeitariaMacedoFarias.entities.User;
import com.example.confeitariaMacedoFarias.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email:admin@confeitaria.local}")
    private String adminEmail;

    @Value("${admin.password:changeMe123}")
    private String adminPassword;

    @PostConstruct
    public void initAdmin() {
        if (userRepository.findByEmail(adminEmail).isPresent()) {
            return;
        }
        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);
    }
}

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
        User admin = userRepository.findByEmail(adminEmail).orElse(null);
        if (admin != null) {
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            return;
        }

        User newAdmin = new User();
        newAdmin.setEmail(adminEmail);
        newAdmin.setPassword(passwordEncoder.encode(adminPassword));
        newAdmin.setRole(Role.ADMIN);
        userRepository.save(newAdmin);
    }
}

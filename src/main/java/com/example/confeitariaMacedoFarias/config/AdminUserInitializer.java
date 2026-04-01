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
            System.out.println("Admin existe. Role atual: " + admin.getRole());
            admin.setRole(Role.ADMIN);
            String storedPassword = admin.getPassword();
            if (storedPassword == null || storedPassword.isBlank()) {
                admin.setPassword(passwordEncoder.encode(adminPassword));
                System.out.println("Admin sem senha, gerando nova senha a partir da config");
            } else if (!isBcryptHash(storedPassword)) {
                admin.setPassword(passwordEncoder.encode(storedPassword));
                System.out.println("Admin com senha em texto, re-criptografando");
            }
            userRepository.save(admin);
            System.out.println("Admin atualizado com role ADMIN");
            return;
        }

        System.out.println("Admin não existe, criando novo:");
        User newAdmin = new User();
        newAdmin.setEmail(adminEmail);
        newAdmin.setPassword(passwordEncoder.encode(adminPassword));
        newAdmin.setRole(Role.ADMIN);
        userRepository.save(newAdmin);
        System.out.println("Admin criado com sucesso com role ADMIN");
    }

    private boolean isBcryptHash(String value) {
        return value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$");
    }
}

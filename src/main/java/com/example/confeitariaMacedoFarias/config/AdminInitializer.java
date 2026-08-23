package com.example.confeitariaMacedoFarias.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.confeitariaMacedoFarias.entities.Role;
import com.example.confeitariaMacedoFarias.entities.User;
import com.example.confeitariaMacedoFarias.repositories.UserRepository;



@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByEmail("admin@macedofarias.com").isEmpty()) {

            User admin = new User();

            admin.setName("Administrador");
            admin.setEmail("admin@macedofarias.com");

            admin.setPassword(
                    passwordEncoder.encode("20051999"));

            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }
    }
}
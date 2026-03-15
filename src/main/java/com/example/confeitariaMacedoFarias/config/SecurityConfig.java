package com.example.confeitariaMacedoFarias.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // Bean para criptografia de senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuração de autorização por rota
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // H2 console (desenvolvimento)
                .requestMatchers("/h2-console/**").permitAll()

                // Públicas
                .requestMatchers("/clients/register", "/clients/login").permitAll()
                .requestMatchers("/products/**").permitAll()
                .requestMatchers("/categories/**").permitAll()
                
                // Apenas Admin
                .requestMatchers("/users/**").hasRole("ADMIN")
                .requestMatchers("/products", "/products/**").hasRole("ADMIN")
                .requestMatchers("/categories/**").hasRole("ADMIN")
                
                // User + Admin
                .requestMatchers("/orders/**").hasAnyRole("USER", "ADMIN")
                
                // Qualquer requisição autenticada
                .anyRequest().authenticated()
            )
            // Permite que o H2 Console seja exibido em <iframe>
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .httpBasic(basic -> {});

        return http.build();
    }
}


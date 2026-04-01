package com.example.confeitariaMacedoFarias.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.confeitariaMacedoFarias.entities.Client;
import com.example.confeitariaMacedoFarias.entities.Role;
import com.example.confeitariaMacedoFarias.entities.User;
import com.example.confeitariaMacedoFarias.repositories.ClientRepository;
import com.example.confeitariaMacedoFarias.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User admin = userRepository.findByEmailIgnoreCase(username).orElse(null);
        if (admin != null) {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Role role = admin.getRole() != null ? admin.getRole() : Role.USER;
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
            System.out.println("Admin user loaded: " + username + " with role: " + role + " authorities: " + authorities);
            return new org.springframework.security.core.userdetails.User(
                    admin.getEmail(), admin.getPassword(), authorities
            );
        }

        Client client = clientRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(
                client.getEmail(), client.getPassword(), authorities
        );
    }
}

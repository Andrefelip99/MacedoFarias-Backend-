package com.example.confeitariaMacedoFarias.services;


import java.security.Key;
import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final Key secretKey;
    private final Duration expiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms:86400000}") long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        this.expiration = Duration.ofMillis(expirationMs);
    }

    public String generateToken(String username) {
        Date issuedAt = new Date();
        return Jwts.builder()
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(new Date(issuedAt.getTime() + expiration.toMillis()))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            String tokenUsername = extractUsername(token);
            return username != null && username.equals(tokenUsername);
        } catch (RuntimeException exception) {
            return false;
        }
    }
}

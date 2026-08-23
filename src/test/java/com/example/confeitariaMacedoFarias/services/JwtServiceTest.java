package com.example.confeitariaMacedoFarias.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JwtServiceTest {

    private static final String SECRET = "MinhaChaveJWTUltraMegaSeguraComMaisDe32Caracteres123456";

    @Test
    void generateTokenShouldContainUsername() {
        JwtService service = new JwtService(SECRET, 86_400_000L);

        String token = service.generateToken("admin@confeitaria.local");

        assertNotNull(token);
        assertEquals("admin@confeitaria.local", service.extractUsername(token));
    }

    @Test
    void tokenShouldBeValidForItsUsername() {
        JwtService service = new JwtService(SECRET, 86_400_000L);
        String token = service.generateToken("admin@confeitaria.local");

        assertTrue(service.isTokenValid(token, "admin@confeitaria.local"));
        assertFalse(service.isTokenValid(token, "other@confeitaria.local"));
    }

    @Test
    void expiredTokenShouldBeInvalid() {
        JwtService service = new JwtService(SECRET, 0L);
        String token = service.generateToken("admin@confeitaria.local");

        assertFalse(service.isTokenValid(token, "admin@confeitaria.local"));
    }

    @Test
    void malformedTokenShouldBeInvalid() {
        JwtService service = new JwtService(SECRET, 86_400_000L);

        assertFalse(service.isTokenValid("token-invalido", "admin@confeitaria.local"));
    }
}

package com.retonttdata.authentication.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    private JwtProvider jwtProvider;
    private static final String SECRET = "testSecret";
    private static final long EXPIRATION = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(SECRET, EXPIRATION);
    }

    @Test
    void generateToken_ValidEmail_ReturnsToken() {
        // Arrange
        String email = "test@example.com";

        // Act
        String token = jwtProvider.generateToken(email);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

//    @Test
//    void validateToken_ValidToken_ReturnsTrue() {
//        // Arrange
//        String email = "test@example.com";
//        String token = jwtProvider.generateToken(email);
//
//        // Act & Assert
//        assertTrue(jwtProvider.validateToken(token));
//    }
//
//    @Test
//    void getEmailFromToken_ValidToken_ReturnsEmail() {
//        // Arrange
//        String email = "test@example.com";
//        String token = jwtProvider.generateToken(email);
//
//        // Act
//        String extractedEmail = jwtProvider.getEmailFromToken(token);
//
//        // Assert
//        assertEquals(email, extractedEmail);
//    }
}
package com.limechain.ethereumfetcher.service;

import com.limechain.ethereumfetcher.exception.JwtParsingException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtServiceTest {

    private JwtService jwtService;

    private Authentication authentication;

    private final String secretKey = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";

    @BeforeEach
    void setUp() {
        this.authentication = Mockito.mock(Authentication.class);
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
    }

    @Test
    void createToken_shouldReturnValidToken() {
        when(authentication.getName()).thenReturn("testUser");

        String token = jwtService.createToken(authentication);

        assertNotNull(token);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals("testUser", extractedUsername);
    }

    @Test
    void extractUsername_shouldReturnCorrectUsername() {
        String token = createTestToken("testUser");

        String username = jwtService.extractUsername(token);

        assertEquals("testUser", username);
    }

    @Test
    void extractAllClaims_shouldThrowJwtParsingExceptionForInvalidToken() {
        String invalidToken = "invalid.token.value";
        ReflectionTestUtils.setField(jwtService, "secretKey", invalidToken);

        Exception exception = assertThrows(JwtParsingException.class, () ->
                jwtService.extractUsername("testUser")
        );

        String expectedMessage = "Cannot parse JWT";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    // Helper method to create a test token
    private String createTestToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts
                .builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}

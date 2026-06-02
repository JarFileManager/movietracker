package com.jatin.movietracker.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;

    private final String secret = "mySecretKeyWithAtLeast256BitsForHS256Algorithm!";
    private final Long expiration = 3600000L;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", secret);
        ReflectionTestUtils.setField(jwtService, "expiration", expiration);
        jwtService.init();
    }

    @Test
    void generateToken_ShouldCreateValidToken() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);

        assertThat(token).isNotNull();
        assertThat(jwtService.extractEmail(token)).isEqualTo(email);
    }

    @Test
    void extractEmail_ShouldReturnCorrectEmail() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);

        String extractedEmail = jwtService.extractEmail(token);
        assertThat(extractedEmail).isEqualTo(email);
    }

    @Test
    void isTokenValid_ShouldReturnTrueForCorrectUser() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);
        UserDetails userDetails = new User(email, "password", new ArrayList<>());

        assertThat(jwtService.isTokenValid(token, userDetails)).isTrue();
    }

    @Test
    void isTokenValid_ShouldReturnFalseForIncorrectUser() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);
        UserDetails userDetails = new User("other@example.com", "password", new ArrayList<>());

        assertThat(jwtService.isTokenValid(token, userDetails)).isFalse();
    }
}

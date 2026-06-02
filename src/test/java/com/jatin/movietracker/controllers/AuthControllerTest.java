package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.LoginRequest;
import com.jatin.movietracker.dtos.requests.SignupRequest;
import com.jatin.movietracker.dtos.responses.AuthResponse;
import com.jatin.movietracker.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void signup_ShouldReturnAuthResponse() {
        SignupRequest request = new SignupRequest("testuser", "test@example.com", "password");
        AuthResponse response = new AuthResponse("jwtToken", 3600L, "testuser");

        when(authService.authenticateSignup(any(SignupRequest.class))).thenReturn(response);

        ResponseEntity<AuthResponse> result = authController.signup(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("testuser", result.getBody().getUsername());
        assertEquals("jwtToken", result.getBody().getToken());
    }

    @Test
    void login_ShouldReturnAuthResponse() {
        LoginRequest request = new LoginRequest("test@example.com", "password");
        AuthResponse response = new AuthResponse("jwtToken", 3600L, "testuser");

        when(authService.authenticateLogin(any(LoginRequest.class))).thenReturn(response);

        ResponseEntity<AuthResponse> result = authController.login(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("testuser", result.getBody().getUsername());
        assertEquals("jwtToken", result.getBody().getToken());
    }
}

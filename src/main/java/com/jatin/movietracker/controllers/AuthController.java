package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.LoginRequest;
import com.jatin.movietracker.dtos.requests.SignupRequest;
import com.jatin.movietracker.dtos.responses.AuthResponse;
import com.jatin.movietracker.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok().body(authService.authenticateSignup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(authService.authenticateLogin(loginRequest));
    }
}

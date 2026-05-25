package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.requests.LoginRequest;
import com.jatin.movietracker.dtos.requests.SignupRequest;
import com.jatin.movietracker.dtos.responses.AuthResponse;
import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.repositories.UserRepository;
import com.jatin.movietracker.security.JwtService;
import com.jatin.movietracker.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;

    private JwtService jwtService;

    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse authenticateSignup(SignupRequest signupRequest) {
        Optional<User> user = userRepository.findByEmail(signupRequest.getEmail());
        if (user.isPresent()) {
            return null;
        }

        User userEntity = AuthUtils.converSignUpRequestToNewUser(signupRequest);
        userRepository.save(userEntity);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUsername(userEntity.getUsername());
        authResponse.setToken(jwtService.generateToken(userEntity.getEmail()));
        authResponse.setExpiresIn(jwtService.getExpiration());
        return authResponse;
    }

    public AuthResponse authenticateLogin(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent()) {
            User userEntity = user.get();
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUsername(userEntity.getUsername());
            authResponse.setToken(jwtService.generateToken(userEntity.getEmail()));
            authResponse.setExpiresIn(jwtService.getExpiration());
            return authResponse;
        }

        return null;
    }
}

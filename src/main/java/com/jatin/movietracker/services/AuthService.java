package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.Role;
import com.jatin.movietracker.dtos.requests.LoginRequest;
import com.jatin.movietracker.dtos.requests.SignupRequest;
import com.jatin.movietracker.dtos.responses.AuthResponse;
import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.exceptions.DuplicateResourceException;
import com.jatin.movietracker.exceptions.ResourceNotFoundException;
import com.jatin.movietracker.repositories.UserRepository;
import com.jatin.movietracker.security.JwtService;
import com.jatin.movietracker.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public AuthResponse authenticateSignup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User user = AuthUtils.converSignUpRequestToNewUser(signupRequest, encodedPassword);
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser.getEmail());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUsername(savedUser.getUsername());
        authResponse.setToken(token);
        authResponse.setExpiresIn(jwtService.getExpiration());

        return AuthUtils.buildAuthResponse(savedUser.getUsername(), token, jwtService.getExpiration());
    }

    public AuthResponse authenticateLogin(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = jwtService.generateToken(user.getEmail());
        return AuthUtils.buildAuthResponse(user.getUsername(), token, jwtService.getExpiration());
    }

    public Role getMyRole(){
        User user = userService.getCurrentUser();
        return user.getRole();
    }
}

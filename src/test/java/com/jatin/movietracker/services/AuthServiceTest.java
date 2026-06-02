package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.requests.LoginRequest;
import com.jatin.movietracker.dtos.requests.SignupRequest;
import com.jatin.movietracker.dtos.responses.AuthResponse;
import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.repositories.UserRepository;
import com.jatin.movietracker.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private SignupRequest signupRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest("testuser", "test@example.com", "password");
        loginRequest = new LoginRequest("test@example.com", "password");
    }

    @Test
    void authenticateSignup_Success() {
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword");
        
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user.getEmail())).thenReturn("jwtToken");
        when(jwtService.getExpiration()).thenReturn(3600L);

        AuthResponse response = authService.authenticateSignup(signupRequest);

        assertThat(response.getUsername()).isEqualTo("testuser");
        assertThat(response.getToken()).isEqualTo("jwtToken");
        assertThat(response.getExpiresIn()).isEqualTo(3600L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void authenticateSignup_EmailExists() {
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> authService.authenticateSignup(signupRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Email already exists");
    }

    @Test
    void authenticateSignup_UsernameExists() {
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> authService.authenticateSignup(signupRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Username already exists");
    }

    @Test
    void authenticateLogin_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail(loginRequest.getEmail());

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user.getEmail())).thenReturn("jwtToken");
        when(jwtService.getExpiration()).thenReturn(3600L);

        AuthResponse response = authService.authenticateLogin(loginRequest);

        assertThat(response.getUsername()).isEqualTo("testuser");
        assertThat(response.getToken()).isEqualTo("jwtToken");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void authenticateLogin_UserNotFound() {
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.authenticateLogin(loginRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }
}

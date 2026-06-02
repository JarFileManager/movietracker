package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.Role;
import com.jatin.movietracker.dtos.requests.SignupRequest;
import com.jatin.movietracker.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuthUtilsTest {

    @Test
    void shouldConvertSignupRequestToNewUser() {
        SignupRequest signupRequest = new SignupRequest("testuser", "test@example.com", "password");
        String encodedPassword = "encodedPassword";

        User user = AuthUtils.converSignUpRequestToNewUser(signupRequest, encodedPassword);

        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void shouldBuildAuthResponse() {
        String username = "testuser";
        String token = "jwtToken";
        Long expiresIn = 3600L;

        com.jatin.movietracker.dtos.responses.AuthResponse response = AuthUtils.buildAuthResponse(username, token, expiresIn);

        assertThat(response.getUsername()).isEqualTo(username);
        assertThat(response.getToken()).isEqualTo(token);
        assertThat(response.getExpiresIn()).isEqualTo(expiresIn);
    }
}

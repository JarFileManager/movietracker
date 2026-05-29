package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.Role;
import com.jatin.movietracker.dtos.requests.SignupRequest;
import com.jatin.movietracker.dtos.responses.AuthResponse;
import com.jatin.movietracker.entities.User;

public class AuthUtils {

    public static User converSignUpRequestToNewUser(SignupRequest  signupRequest, String encodedPassword) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER); //TODO: Later need to incorporate logic for deciding Role.
        return user;
    }

    public static AuthResponse buildAuthResponse(String username, String token, Long expiresIn) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUsername(username);
        authResponse.setToken(token);
        authResponse.setExpiresIn(expiresIn);
        return authResponse;
    }
}

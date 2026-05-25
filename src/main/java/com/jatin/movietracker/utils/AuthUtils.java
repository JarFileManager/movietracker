package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.Role;
import com.jatin.movietracker.dtos.requests.SignupRequest;
import com.jatin.movietracker.entities.User;

public class AuthUtils {

    public static User converSignUpRequestToNewUser(SignupRequest  signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setUsername(signupRequest.getUsername());

        user.setRole(Role.ADMIN); //Default Role for now.
        return user;
    }
}

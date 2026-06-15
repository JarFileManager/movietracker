package com.jatin.movietracker.services;

import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.exceptions.UnauthorizedException;
import com.jatin.movietracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new UnauthorizedException("You are not authorized"));
    }

}

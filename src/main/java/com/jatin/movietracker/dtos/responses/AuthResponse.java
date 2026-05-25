package com.jatin.movietracker.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private Long expiresIn;
    private String username;
}

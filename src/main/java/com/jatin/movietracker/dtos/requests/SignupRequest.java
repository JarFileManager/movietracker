package com.jatin.movietracker.dtos.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String email;
    private String password;
}

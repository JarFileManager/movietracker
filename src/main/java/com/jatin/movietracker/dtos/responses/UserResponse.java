package com.jatin.movietracker.dtos.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
}

package com.jatin.movietracker.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private UUID id;
    private String username;
    private String apiMovieId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}

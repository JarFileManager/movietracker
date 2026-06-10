package com.jatin.movietracker.dtos.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private UUID id;
    private String username;
    private Long apiMovieId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}

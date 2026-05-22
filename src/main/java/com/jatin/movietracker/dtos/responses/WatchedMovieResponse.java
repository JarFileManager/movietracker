package com.jatin.movietracker.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchedMovieResponse {
    private UUID id;
    private Long apiMovieId;
    private Boolean watched;
    private LocalDateTime createdAt;
}

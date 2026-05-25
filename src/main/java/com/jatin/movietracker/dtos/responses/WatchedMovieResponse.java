package com.jatin.movietracker.dtos.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchedMovieResponse {
    private UUID id;
    private Long apiMovieId;
    private Boolean watched;
    private LocalDateTime createdAt;
}

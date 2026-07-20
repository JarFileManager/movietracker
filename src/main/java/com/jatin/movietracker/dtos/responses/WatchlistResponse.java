package com.jatin.movietracker.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class WatchlistResponse {

    private UUID id;

    private Integer apiMovieId;

    private String movieTitle;

    private LocalDateTime createdAt;
}
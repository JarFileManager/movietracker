package com.jatin.movietracker.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchlistRequest {

    private Integer apiMovieId;

    private String movieTitle;
}
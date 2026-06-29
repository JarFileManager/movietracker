package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.responses.WatchedMovieResponse;
import com.jatin.movietracker.entities.WatchedMovie;

public class WatchUtils {

    public static WatchedMovieResponse watchedMovieToWatchedMovieResponseConverter(WatchedMovie movie) {
        WatchedMovieResponse response = new WatchedMovieResponse();
        response.setId(movie.getId());
        response.setApiMovieId(movie.getApiMovieId());
        response.setCreatedAt(movie.getCreatedAt());
        response.setMovieTitle(movie.getMovieTitle());
        return response;
    }
}

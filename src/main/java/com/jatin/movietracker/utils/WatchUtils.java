package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.responses.WatchedMovieResponse;
import com.jatin.movietracker.dtos.responses.WatchlistResponse;
import com.jatin.movietracker.entities.WatchedMovie;
import com.jatin.movietracker.entities.Watchlist;

public class WatchUtils {

    public static WatchedMovieResponse watchedMovieToWatchedMovieResponseConverter(WatchedMovie movie) {
        WatchedMovieResponse response = new WatchedMovieResponse();
        response.setId(movie.getId());
        response.setApiMovieId(movie.getApiMovieId());
        response.setCreatedAt(movie.getCreatedAt());
        response.setMovieTitle(movie.getMovieTitle());
        return response;
    }

    public static WatchlistResponse watchlistToWatchlistResponseConverter(Watchlist watchlist) {
        WatchlistResponse response = new WatchlistResponse();
        response.setId(watchlist.getId());
        response.setApiMovieId(watchlist.getApiMovieId());
        response.setCreatedAt(watchlist.getCreatedAt());
        response.setMovieTitle(watchlist.getMovieTitle());
        return response;
    }
}

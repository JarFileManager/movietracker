package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.responses.ApiMovieResponse;
import com.jatin.movietracker.tmdb.TmdbClient;
import com.jatin.movietracker.tmdb.TmdbMovie;
import com.jatin.movietracker.tmdb.TmdbSearchResponse;
import com.jatin.movietracker.utils.MovieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MovieService {

    private final TmdbClient tmdbClient;

    @Autowired
    public MovieService(TmdbClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    public List<ApiMovieResponse> searchMovies(String query) {
        TmdbSearchResponse response = tmdbClient.searchMovies(query);

        return response
                .getResults()
                .stream()
                .map(MovieUtils::convert)
                .toList();
    }

    public ApiMovieResponse getMovie(Long movieId) {
        TmdbMovie movie = tmdbClient.getMovie(movieId);
        return MovieUtils.convert(movie);
    }
}
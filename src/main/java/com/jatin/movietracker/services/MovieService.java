package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.requests.GetRandomMovieRequest;
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
    private final PreferenceService preferenceService;

    @Autowired
    public MovieService(TmdbClient tmdbClient, PreferenceService preferenceService) {
        this.tmdbClient = tmdbClient;
        this.preferenceService = preferenceService;
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

    public ApiMovieResponse getRandomMovie() {
        GetRandomMovieRequest request = preferenceService.getMyPreferences();
        TmdbSearchResponse response = tmdbClient.getRandomMovie(request);
        return MovieUtils.convert(response.getResults().get(MovieUtils.getRandomInt(0, response.getResults().size() - 1)));
    }

    public List<ApiMovieResponse> discoverMoviesWithFilters(GetRandomMovieRequest request) {
        TmdbSearchResponse response = tmdbClient.getRandomMovie(request);
        return response
                .getResults()
                .stream()
                .map(MovieUtils::convert)
                .toList();
    }

    public List<ApiMovieResponse> getTrendingMovies() {
        return tmdbClient.getTrendingMovies()
                .stream()
                .map(MovieUtils::convert)
                .toList();
    }

    public List<ApiMovieResponse> getTopRatedMovies() {
        return tmdbClient.getTopRatedMovies()
                .stream()
                .map(MovieUtils::convert)
                .toList();
    }

    public List<ApiMovieResponse> getNowPlayingMovies() {
        return tmdbClient.getNowPlayingMovies()
                .stream()
                .map(MovieUtils::convert)
                .toList();
    }

    public List<ApiMovieResponse> getTrendingTvShows() {
        return tmdbClient.getTrendingTvShows()
                .stream()
                .map(MovieUtils::convert)
                .toList();
    }
}
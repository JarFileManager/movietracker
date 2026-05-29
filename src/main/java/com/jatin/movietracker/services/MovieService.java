package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.responses.ApiMovieResponse;
import com.jatin.movietracker.tmdb.TmdbClient;
import com.jatin.movietracker.tmdb.TmdbMovie;
import com.jatin.movietracker.tmdb.TmdbSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private static final String IMAGE_BASE_URL =
            "https://image.tmdb.org/t/p/w500";

    private final TmdbClient tmdbClient;

    public List<ApiMovieResponse> searchMovies(String query) {
        TmdbSearchResponse response = tmdbClient.searchMovies(query);

        return response
                .getResults()
                .stream()
                .map(this::convert)
                .toList();
    }

    public ApiMovieResponse getMovie(Long movieId) {
        TmdbMovie movie = tmdbClient.getMovie(movieId);
        return convert(movie);
    }

    private ApiMovieResponse convert(TmdbMovie movie) {

        ApiMovieResponse response = new ApiMovieResponse();

        response.setId(movie.getId());
        response.setTitle(movie.getTitle());
        response.setOverview(movie.getOverview());
        response.setRating(movie.getVote_average());
        response.setReleaseDate(getDateTime(movie.getRelease_date()));
        response.setGenreIds(movie.getGenre_ids());
        response.setBackdropPath(IMAGE_BASE_URL + movie.getBackdrop_path());
        response.setPosterUrl(IMAGE_BASE_URL + movie.getPoster_path());

        return response;
    }

    private LocalDateTime getDateTime(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
    }
}
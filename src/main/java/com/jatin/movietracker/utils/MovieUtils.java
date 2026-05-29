package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.responses.ApiMovieResponse;
import com.jatin.movietracker.tmdb.TmdbMovie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovieUtils {

    private static final String IMAGE_BASE_URL =
            "https://image.tmdb.org/t/p/w500";

    public static ApiMovieResponse convert(TmdbMovie movie){
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

    public static LocalDateTime getDateTime(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
    }
}

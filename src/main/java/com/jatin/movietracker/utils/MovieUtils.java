package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.responses.ApiMovieResponse;
import com.jatin.movietracker.tmdb.TmdbMovie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MovieUtils {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    public static ApiMovieResponse convert(TmdbMovie movie){
        ApiMovieResponse response = new ApiMovieResponse();
        String title = movie.getTitle() != null ? movie.getTitle() : movie.getName();
        response.setId(movie.getId());
        response.setTitle(title);
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

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static String convertIntegerListToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if(i != list.size() - 1) {
                sb.append("%2C");
            }
        }
        return sb.toString();
    }
}

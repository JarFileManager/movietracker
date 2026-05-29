package com.jatin.movietracker.tmdb;

import lombok.Data;
import java.util.List;

@Data
public class TmdbMovie {

    private Long id;
    private String title;
    private String overview;
    private String poster_path;
    private Double vote_average;
    private String release_date;
    private String backdrop_path;
    private List<Integer> genre_ids;
}
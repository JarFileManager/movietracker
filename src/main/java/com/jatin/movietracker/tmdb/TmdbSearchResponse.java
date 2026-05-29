package com.jatin.movietracker.tmdb;

import lombok.Data;
import java.util.List;

@Data
public class TmdbSearchResponse {
    private Integer page;
    private List<TmdbMovie> results;
}
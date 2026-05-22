package com.jatin.movietracker.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiMovieResponse {
    private Long id;
    private String title;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private LocalDateTime releaseDate;
    private Double voteAverage;
    private List<Integer> genreIds;
}

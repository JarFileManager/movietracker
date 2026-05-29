package com.jatin.movietracker.dtos.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiMovieResponse {
    private Long id;
    private String title;
    private String overview;
    private String posterUrl;
    private String backdropPath;
    private LocalDateTime releaseDate;
    private Double rating;
    private List<Integer> genreIds;
}

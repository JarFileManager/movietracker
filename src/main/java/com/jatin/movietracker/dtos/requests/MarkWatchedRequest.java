package com.jatin.movietracker.dtos.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarkWatchedRequest {
    private Long apiMovieId;
    private Boolean watched;
    private String movieTitle;
}

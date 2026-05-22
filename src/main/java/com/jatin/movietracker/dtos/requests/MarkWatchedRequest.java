package com.jatin.movietracker.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkWatchedRequest {
    private Long apiMovieId;
    private Boolean watched;
}

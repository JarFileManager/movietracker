package com.jatin.movietracker.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequest {
    private Long apiMovieId;
    private String comment;
    private Integer rating;
}

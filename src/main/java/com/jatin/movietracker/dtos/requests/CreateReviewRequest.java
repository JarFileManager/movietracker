package com.jatin.movietracker.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequest {
    private Long apiMovieId;
    private String comment;

    @Min(1)
    @Max(5)
    private Integer rating;
    private String movieTitle;
}

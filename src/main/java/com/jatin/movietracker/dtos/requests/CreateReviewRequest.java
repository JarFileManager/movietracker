package com.jatin.movietracker.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequest {
    private Long apiMovieId;
    private Text comment;
    private Integer rating;
}

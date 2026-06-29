package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.responses.ReviewResponse;
import com.jatin.movietracker.entities.Review;

public class ReviewUtils {

    public static ReviewResponse reviewToReviewResponseConverter(Review review) {
        ReviewResponse response = new ReviewResponse();

        response.setId(review.getId());
        response.setApiMovieId(review.getApiMovieId());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setUsername(review.getUser().getUsername());
        response.setCreatedAt(review.getCreatedAt());
        response.setMovieTitle(review.getMovieTitle());

        return response;
    }
}

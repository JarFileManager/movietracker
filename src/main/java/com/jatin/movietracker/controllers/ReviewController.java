package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.CreateReviewRequest;
import com.jatin.movietracker.dtos.responses.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@RequestBody CreateReviewRequest reviewRequest) {
        return ResponseEntity.ok().body(new ReviewResponse());
    }

    @GetMapping("/me/{apiMovieId}")
    public ResponseEntity<ReviewResponse> getMyReview(@PathVariable Long apiMovieId) {
        return ResponseEntity.ok().body(new ReviewResponse());
    }

    @GetMapping("/me")
    public ResponseEntity<List<ReviewResponse>> getMyReviews() {
        return ResponseEntity.ok().body(List.of(new ReviewResponse()));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable UUID reviewId, @RequestBody CreateReviewRequest createReviewRequest) {
        return ResponseEntity.ok().body(new ReviewResponse());
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable UUID reviewId) {
        return ResponseEntity.ok().body(new ReviewResponse());
    }

}

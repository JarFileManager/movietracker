package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.CreateReviewRequest;
import com.jatin.movietracker.dtos.responses.ReviewResponse;
import com.jatin.movietracker.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@RequestBody CreateReviewRequest reviewRequest) {
        return ResponseEntity.ok().body(reviewService.addReview(reviewRequest));
    }

    @GetMapping("/me/{apiMovieId}")
    public ResponseEntity<ReviewResponse> getMyReview(@PathVariable Long apiMovieId) {
        ReviewResponse reviewResponse = reviewService.getMyReview(apiMovieId);
        if (reviewResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(reviewResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<Page<ReviewResponse>> getMyReviews(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getMyMovieReviews(page, size));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable UUID reviewId, @RequestBody CreateReviewRequest createReviewRequest) {
        return ResponseEntity.ok().body(reviewService.updateReview(reviewId, createReviewRequest));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().body("Review with id " + reviewId + " has been deleted");
    }

}

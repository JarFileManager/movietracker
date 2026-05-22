package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.CreateReviewRequest;
import com.jatin.movietracker.dtos.requests.LoginRequest;
import com.jatin.movietracker.dtos.requests.MarkWatchedRequest;
import com.jatin.movietracker.dtos.requests.SignupRequest;
import com.jatin.movietracker.dtos.responses.ApiMovieResponse;
import com.jatin.movietracker.dtos.responses.AuthResponse;
import com.jatin.movietracker.dtos.responses.ReviewResponse;
import com.jatin.movietracker.dtos.responses.WatchedMovieResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok().body(new AuthResponse());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(new AuthResponse());
    }

    @GetMapping("/movies/random")
    public ResponseEntity<ApiMovieResponse> getRandomMovies() {
        return ResponseEntity.ok().body(new ApiMovieResponse());
    }

    @GetMapping("/movies/search")
    public ResponseEntity<ApiMovieResponse> getRandomMovies(@RequestParam String query) {
        return ResponseEntity.ok().body(new ApiMovieResponse());
    }

    @GetMapping("/movies/genre/{genreId}")
    public ResponseEntity<List<ApiMovieResponse>> getMoviesByGenre(@PathVariable Integer genreId) {
        return ResponseEntity.ok().body(List.of(new ApiMovieResponse()));
    }

    @GetMapping("/movies/{movieId}")
    public ResponseEntity<ApiMovieResponse> getMovie(@PathVariable Integer movieId) {
        return ResponseEntity.ok().body(new ApiMovieResponse());
    }

    @PostMapping("/watched")
    public ResponseEntity<WatchedMovieResponse> markMovieAsWatched(@RequestBody MarkWatchedRequest  markWatchedRequest) {
        return ResponseEntity.ok().body(new WatchedMovieResponse());
    }

    @GetMapping("/watched/me")
    public ResponseEntity<List<WatchedMovieResponse>> getUserWatchedMovies() {
        return ResponseEntity.ok().body(List.of(new WatchedMovieResponse()));
    }

    @DeleteMapping("/watched/{apiMovieId}")
    public ResponseEntity<String> deleteWatchedMovie(@PathVariable Long apiMovieId) {
        return ResponseEntity.ok().body("Movie with id " + apiMovieId + " has been marked as not watched");
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponse> addReview(@RequestBody CreateReviewRequest reviewRequest) {
        return ResponseEntity.ok().body(new ReviewResponse());
    }

    @GetMapping("/reviews/{apiMovieId}")
    public ResponseEntity<ReviewResponse> getMyReview(@PathVariable Long apiMovieId) {
        return ResponseEntity.ok().body(new ReviewResponse());
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable UUID reviewId, @RequestBody CreateReviewRequest createReviewRequest) {
        return ResponseEntity.ok().body(new ReviewResponse());
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable UUID reviewId) {
        return ResponseEntity.ok().body(new ReviewResponse());
    }

}

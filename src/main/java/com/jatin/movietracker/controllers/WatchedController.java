package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.MarkWatchedRequest;
import com.jatin.movietracker.dtos.responses.WatchedMovieResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/watched")
public class WatchedController {

    @PostMapping
    public ResponseEntity<WatchedMovieResponse> markMovieAsWatched(@RequestBody MarkWatchedRequest markWatchedRequest) {
        return ResponseEntity.ok().body(new WatchedMovieResponse());
    }

    @GetMapping("/me")
    public ResponseEntity<List<WatchedMovieResponse>> getUserWatchedMovies() {
        return ResponseEntity.ok().body(List.of(new WatchedMovieResponse()));
    }

    @DeleteMapping("/{apiMovieId}")
    public ResponseEntity<String> deleteWatchedMovie(@PathVariable Long apiMovieId) {
        return ResponseEntity.ok().body("Movie with id " + apiMovieId + " has been marked as not watched");
    }
}

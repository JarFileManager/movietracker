package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.MarkWatchedRequest;
import com.jatin.movietracker.dtos.responses.WatchedMovieResponse;
import com.jatin.movietracker.services.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/watched")
public class WatchedController {

    private final WatchService watchService;

    @Autowired
    public WatchedController(WatchService watchService) {
        this.watchService = watchService;
    }

    @PostMapping
    public ResponseEntity<WatchedMovieResponse> markMovieAsWatched(@RequestBody MarkWatchedRequest markWatchedRequest) {
        return ResponseEntity.ok().body(watchService.markMovieAsWatched(markWatchedRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<List<WatchedMovieResponse>> getUserWatchedMovies() {
        return ResponseEntity.ok().body(watchService.getUserWatchedMovies());
    }

    @DeleteMapping("/{apiMovieId}")
    public ResponseEntity<String> deleteWatchedMovie(@PathVariable Long apiMovieId) {
        watchService.deleteWatchedMovie(apiMovieId);
        return ResponseEntity.ok().body("Movie with id " + apiMovieId + " has been marked as not watched");
    }
}

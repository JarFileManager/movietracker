package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.responses.ApiMovieResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    @GetMapping("/random")
    public ResponseEntity<ApiMovieResponse> getRandomMovies() {
        return ResponseEntity.ok().body(new ApiMovieResponse());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiMovieResponse> getRandomMovies(@RequestParam String query) {
        return ResponseEntity.ok().body(new ApiMovieResponse());
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<ApiMovieResponse>> getMoviesByGenre(@PathVariable Integer genreId) {
        return ResponseEntity.ok().body(List.of(new ApiMovieResponse()));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ApiMovieResponse> getMovie(@PathVariable Integer movieId) {
        return ResponseEntity.ok().body(new ApiMovieResponse());
    }
}

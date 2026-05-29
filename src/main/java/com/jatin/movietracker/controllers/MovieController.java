package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.responses.ApiMovieResponse;
import com.jatin.movietracker.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/random")
    public ResponseEntity<ApiMovieResponse> getRandomMovies() {
        return ResponseEntity.ok().body(new ApiMovieResponse());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ApiMovieResponse>> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<ApiMovieResponse>> getMoviesByGenre(@PathVariable Integer genreId) {
        return ResponseEntity.ok().body(List.of(new ApiMovieResponse()));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ApiMovieResponse> getMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }
}

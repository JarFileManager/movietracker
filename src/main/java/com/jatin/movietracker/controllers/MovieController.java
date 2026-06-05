package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.GetRandomMovieRequest;
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
    public ResponseEntity<ApiMovieResponse> getRandomMovie() {
        return ResponseEntity.ok().body(movieService.getRandomMovie());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ApiMovieResponse>> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }

    @GetMapping("/discover")
    public ResponseEntity<List<ApiMovieResponse>> discoverMoviesWithFilters(@RequestBody GetRandomMovieRequest request) {
        return ResponseEntity.ok().body(movieService.discoverMoviesWithFilters(request));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ApiMovieResponse> getMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }
}

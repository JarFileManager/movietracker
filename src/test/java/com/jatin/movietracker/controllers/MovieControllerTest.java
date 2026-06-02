package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.responses.ApiMovieResponse;
import com.jatin.movietracker.services.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @Test
    void searchMovies_ShouldReturnMovies() {
        String query = "Inception";
        ApiMovieResponse movie = new ApiMovieResponse();
        movie.setId(1L);
        movie.setTitle("Inception");

        when(movieService.searchMovies(query)).thenReturn(List.of(movie));

        ResponseEntity<List<ApiMovieResponse>> result = movieController.searchMovies(query);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("Inception", result.getBody().get(0).getTitle());
    }

    @Test
    void getMovie_ShouldReturnMovie() {
        Long movieId = 1L;
        ApiMovieResponse movie = new ApiMovieResponse();
        movie.setId(movieId);
        movie.setTitle("Inception");

        when(movieService.getMovie(movieId)).thenReturn(movie);

        ResponseEntity<ApiMovieResponse> result = movieController.getMovie(movieId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Inception", result.getBody().getTitle());
    }
}

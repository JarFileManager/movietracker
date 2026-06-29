package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.MarkWatchedRequest;
import com.jatin.movietracker.dtos.responses.WatchedMovieResponse;
import com.jatin.movietracker.services.WatchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WatchedControllerTest {

    @Mock
    private WatchService watchService;

    @InjectMocks
    private WatchedController watchedController;

    @Test
    void markMovieAsWatched_ShouldReturnWatchedMovieResponse() {
        MarkWatchedRequest request = new MarkWatchedRequest(1L, "My Movie");
        WatchedMovieResponse response = new WatchedMovieResponse();
        response.setId(UUID.randomUUID());
        response.setApiMovieId(1L);
        response.setMovieTitle("My Movie");

        when(watchService.markMovieAsWatched(any(MarkWatchedRequest.class))).thenReturn(response);

        ResponseEntity<WatchedMovieResponse> result = watchedController.markMovieAsWatched(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getApiMovieId()).isEqualTo(1L);
        assertThat(result.getBody().getMovieTitle()).isEqualTo("My Movie");
    }

    @Test
    void getUserWatchedMovies_ShouldReturnListOfWatchedMovieResponse() {
        WatchedMovieResponse response = new WatchedMovieResponse();
        response.setApiMovieId(1L);
        List<WatchedMovieResponse> responses = List.of(response);

        when(watchService.getUserWatchedMovies()).thenReturn(responses);

        ResponseEntity<List<WatchedMovieResponse>> result = watchedController.getUserWatchedMovies();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody().get(0).getApiMovieId()).isEqualTo(1L);
    }

    @Test
    void deleteWatchedMovie_ShouldReturnSuccessMessage() {
        Long apiMovieId = 1L;
        doNothing().when(watchService).deleteWatchedMovie(apiMovieId);

        ResponseEntity<String> result = watchedController.deleteWatchedMovie(apiMovieId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("Movie with id " + apiMovieId + " has been marked as not watched");
    }
}

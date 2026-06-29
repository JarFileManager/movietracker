package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.requests.MarkWatchedRequest;
import com.jatin.movietracker.dtos.responses.WatchedMovieResponse;
import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.entities.WatchedMovie;
import com.jatin.movietracker.repositories.WatchedMovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private WatchedMovieRepository watchedMovieRepository;

    @InjectMocks
    private WatchService watchService;

    @Test
    void markMovieAsWatched_NewMovie_ShouldCreateAndReturnResponse() {
        MarkWatchedRequest request = new MarkWatchedRequest(1L,  "My Movie");
        User user = new User();
        user.setId(UUID.randomUUID());

        when(userService.getCurrentUser()).thenReturn(user);
        when(watchedMovieRepository.findByUserAndApiMovieId(user, 1L)).thenReturn(Optional.empty());
        
        WatchedMovie savedMovie = new WatchedMovie();
        savedMovie.setId(UUID.randomUUID());
        savedMovie.setUser(user);
        savedMovie.setApiMovieId(1L);
        savedMovie.setMovieTitle("My Movie");

        when(watchedMovieRepository.save(any(WatchedMovie.class))).thenReturn(savedMovie);

        WatchedMovieResponse result = watchService.markMovieAsWatched(request);

        assertThat(result).isNotNull();
        assertThat(result.getApiMovieId()).isEqualTo(1L);
        assertThat(result.getMovieTitle()).isEqualTo("My Movie");
        verify(watchedMovieRepository, times(1)).save(any(WatchedMovie.class));
    }

    @Test
    void markMovieAsWatched_ExistingMovie_ShouldUpdateAndReturnResponse() {
        MarkWatchedRequest request = new MarkWatchedRequest(1L,  "My Movie");
        User user = new User();
        user.setId(UUID.randomUUID());

        WatchedMovie existingMovie = new WatchedMovie();
        existingMovie.setId(UUID.randomUUID());
        existingMovie.setUser(user);
        existingMovie.setApiMovieId(1L);
        existingMovie.setMovieTitle("My Movie");

        when(userService.getCurrentUser()).thenReturn(user);
        when(watchedMovieRepository.findByUserAndApiMovieId(user, 1L)).thenReturn(Optional.of(existingMovie));
        when(watchedMovieRepository.save(existingMovie)).thenReturn(existingMovie);

        WatchedMovieResponse result = watchService.markMovieAsWatched(request);

        assertThat(result).isNotNull();
        assertThat(result.getMovieTitle()).isEqualTo("My Movie");
        verify(watchedMovieRepository, times(1)).save(existingMovie);
    }

    @Test
    void getUserWatchedMovies_NotEmpty_ShouldReturnList() {
        User user = new User();
        WatchedMovie movie = new WatchedMovie();
        movie.setApiMovieId(1L);
        movie.setMovieTitle("My Movie");

        when(userService.getCurrentUser()).thenReturn(user);
        when(watchedMovieRepository.findByUser(user)).thenReturn(List.of(movie));

        List<WatchedMovieResponse> result = watchService.getUserWatchedMovies();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getApiMovieId()).isEqualTo(1L);
    }

    @Test
    void getUserWatchedMovies_Empty_ShouldReturnEmptyList() {
        User user = new User();

        when(userService.getCurrentUser()).thenReturn(user);
        when(watchedMovieRepository.findByUser(user)).thenReturn(Collections.emptyList());

        List<WatchedMovieResponse> result = watchService.getUserWatchedMovies();

        assertThat(result).isEmpty();
    }

    @Test
    void deleteWatchedMovie_ShouldDeleteSuccessfully() {
        Long apiMovieId = 1L;
        User user = new User();
        WatchedMovie movie = new WatchedMovie();

        when(userService.getCurrentUser()).thenReturn(user);
        when(watchedMovieRepository.findByUserAndApiMovieId(user, apiMovieId)).thenReturn(Optional.of(movie));
        doNothing().when(watchedMovieRepository).delete(movie);

        watchService.deleteWatchedMovie(apiMovieId);

        verify(watchedMovieRepository, times(1)).delete(movie);
    }
}

package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.responses.WatchedMovieResponse;
import com.jatin.movietracker.entities.WatchedMovie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WatchUtilsTest {

    @Test
    void shouldConvertWatchedMovieToWatchedMovieResponse() {
        WatchedMovie movie = new WatchedMovie();
        movie.setId(UUID.randomUUID());
        movie.setApiMovieId(1L);
        movie.setCreatedAt(LocalDateTime.now());

        WatchedMovieResponse response = WatchUtils.watchedMovieToWatchedMovieResponseConverter(movie);

        assertThat(response.getId()).isEqualTo(movie.getId());
        assertThat(response.getApiMovieId()).isEqualTo(movie.getApiMovieId());
        assertThat(response.getCreatedAt()).isEqualTo(movie.getCreatedAt());
    }
}

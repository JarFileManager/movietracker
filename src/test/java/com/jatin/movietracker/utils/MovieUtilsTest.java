package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.responses.ApiMovieResponse;
import com.jatin.movietracker.tmdb.TmdbMovie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MovieUtilsTest {

    @Test
    void shouldConvertTmdbMovieToApiMovieResponse() {
        TmdbMovie tmdbMovie = new TmdbMovie();
        tmdbMovie.setId(1L);
        tmdbMovie.setTitle("Test Movie");
        tmdbMovie.setOverview("Test Overview");
        tmdbMovie.setVote_average(8.5);
        tmdbMovie.setRelease_date("2023-01-01");
        tmdbMovie.setGenre_ids(List.of(1, 2));
        tmdbMovie.setBackdrop_path("/backdrop.jpg");
        tmdbMovie.setPoster_path("/poster.jpg");

        ApiMovieResponse response = MovieUtils.convert(tmdbMovie);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Test Movie");
        assertThat(response.getOverview()).isEqualTo("Test Overview");
        assertThat(response.getRating()).isEqualTo(8.5);
        assertThat(response.getReleaseDate()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));
        assertThat(response.getGenreIds()).containsExactly(1, 2);
        assertThat(response.getBackdropPath()).isEqualTo("https://image.tmdb.org/t/p/w500/backdrop.jpg");
        assertThat(response.getPosterUrl()).isEqualTo("https://image.tmdb.org/t/p/w500/poster.jpg");
    }

    @Test
    void shouldGetDateTimeFromValidDateString() {
        String date = "2023-01-01";
        LocalDateTime dateTime = MovieUtils.getDateTime(date);
        assertThat(dateTime).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));
    }

    @Test
    void shouldReturnNullForNullOrBlankDate() {
        assertThat(MovieUtils.getDateTime(null)).isNull();
        assertThat(MovieUtils.getDateTime("")).isNull();
        assertThat(MovieUtils.getDateTime("   ")).isNull();
    }

    @Test
    void shouldReturnRandomIntWithinRange() {
        int min = 1;
        int max = 10;
        int result = MovieUtils.getRandomInt(min, max);
        assertThat(result).isBetween(min, max);
    }

    @Test
    void shouldConvertIntegerListToString() {
        List<Integer> list = List.of(1, 2, 3);
        String result = MovieUtils.convertIntegerListToString(list);
        assertThat(result).isEqualTo("1%2C2%2C3");
    }

    @Test
    void shouldConvertSingleItemIntegerListToString() {
        List<Integer> list = List.of(1);
        String result = MovieUtils.convertIntegerListToString(list);
        assertThat(result).isEqualTo("1");
    }
}

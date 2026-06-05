package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.requests.GetRandomMovieRequest;
import com.jatin.movietracker.dtos.responses.ApiMovieResponse;
import com.jatin.movietracker.tmdb.TmdbClient;
import com.jatin.movietracker.tmdb.TmdbMovie;
import com.jatin.movietracker.tmdb.TmdbSearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private TmdbClient tmdbClient;

    @InjectMocks
    private MovieService movieService;

    @Test
    void searchMovies_ShouldReturnListOfMovies() {
        String query = "Inception";
        TmdbMovie movie = new TmdbMovie();
        movie.setId(1L);
        movie.setTitle("Inception");
        
        TmdbSearchResponse searchResponse = new TmdbSearchResponse();
        searchResponse.setResults(List.of(movie));

        when(tmdbClient.searchMovies(query)).thenReturn(searchResponse);

        List<ApiMovieResponse> results = movieService.searchMovies(query);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Inception");
    }

    @Test
    void getMovie_ShouldReturnMovie() {
        Long movieId = 1L;
        TmdbMovie movie = new TmdbMovie();
        movie.setId(movieId);
        movie.setTitle("Inception");

        when(tmdbClient.getMovie(movieId)).thenReturn(movie);

        ApiMovieResponse result = movieService.getMovie(movieId);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Inception");
    }

    @Test
    void getRandomMovie_ShouldReturnMovie() {
        TmdbMovie movie = new TmdbMovie();
        movie.setId(1L);
        movie.setTitle("Interstellar");

        TmdbSearchResponse searchResponse = new TmdbSearchResponse();
        searchResponse.setResults(List.of(movie));

        when(tmdbClient.getRandomMovie(null)).thenReturn(searchResponse);

        ApiMovieResponse result = movieService.getRandomMovie();

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Interstellar");
    }

    @Test
    void discoverMoviesWithFilters_ShouldReturnMovies() {
        GetRandomMovieRequest request = new GetRandomMovieRequest();
        TmdbMovie movie = new TmdbMovie();
        movie.setId(1L);
        movie.setTitle("The Dark Knight");

        TmdbSearchResponse searchResponse = new TmdbSearchResponse();
        searchResponse.setResults(List.of(movie));

        when(tmdbClient.getRandomMovie(request)).thenReturn(searchResponse);

        List<ApiMovieResponse> results = movieService.discoverMoviesWithFilters(request);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("The Dark Knight");
    }
}

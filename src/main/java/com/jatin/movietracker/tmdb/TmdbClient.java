package com.jatin.movietracker.tmdb;

import com.jatin.movietracker.dtos.requests.GetRandomMovieRequest;
import com.jatin.movietracker.utils.MovieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class TmdbClient {

    private final RestTemplate restTemplate;

    private final TmdbProperties tmdbProperties;

    @Autowired
    public TmdbClient(RestTemplate restTemplate, TmdbProperties tmdbProperties) {
        this.restTemplate = restTemplate;
        this.tmdbProperties = tmdbProperties;
    }

    public TmdbSearchResponse searchMovies(String query) {
        String url = tmdbProperties.getBaseUrl() + "/search/movie" + "?query=" + query;
        HttpEntity<Void> entity = createEntity();
        return restTemplate.exchange(url, HttpMethod.GET, entity, TmdbSearchResponse.class).getBody();
    }

    public TmdbMovie getMovie(Long movieId) {
        String url = tmdbProperties.getBaseUrl() + "/movie/" + movieId;
        HttpEntity<Void> entity = createEntity();
        return restTemplate.exchange(url, HttpMethod.GET, entity, TmdbMovie.class).getBody();
    }

    public TmdbSearchResponse getRandomMovie(GetRandomMovieRequest request) {
        String url = tmdbProperties.getBaseUrl() + "/discover/movie";
        if (request != null) {
            url = addFiltersToUrl(request, url);
        }
        HttpEntity<Void> entity = createEntity();
        return restTemplate.exchange(url, HttpMethod.GET, entity, TmdbSearchResponse.class).getBody();
    }

    public List<TmdbMovie> getTrendingMovies() {
        String url = tmdbProperties.getBaseUrl() + "/trending/movie/day";
        HttpEntity<Void> entity = createEntity();
        TmdbSearchResponse response = restTemplate.exchange(url, HttpMethod.GET, entity, TmdbSearchResponse.class).getBody();
        if(response == null){
            return Collections.emptyList();
        }
        return response.getResults();
    }

    public List<TmdbMovie> getTopRatedMovies() {
        String url = tmdbProperties.getBaseUrl() + "/movie/top_rated";
        HttpEntity<Void> entity = createEntity();
        TmdbSearchResponse response = restTemplate.exchange(url, HttpMethod.GET, entity, TmdbSearchResponse.class).getBody();
        if(response == null){
            return Collections.emptyList();
        }
        return response.getResults();
    }

    public List<TmdbMovie> getNowPlayingMovies() {
        String url = tmdbProperties.getBaseUrl() + "/movie/now_playing";
        HttpEntity<Void> entity = createEntity();
        TmdbSearchResponse response = restTemplate.exchange(url, HttpMethod.GET, entity, TmdbSearchResponse.class).getBody();
        if(response == null){
            return Collections.emptyList();
        }
        return response.getResults();
    }

    public List<TmdbMovie> getTrendingTvShows() {
        String url = tmdbProperties.getBaseUrl() + "/trending/tv/day";
        HttpEntity<Void> entity = createEntity();
        TmdbSearchResponse response = restTemplate.exchange(url, HttpMethod.GET, entity, TmdbSearchResponse.class).getBody();
        if(response == null){
            return Collections.emptyList();
        }
        return response.getResults();
    }

    private HttpEntity<Void> createEntity(){
        String accessToken = tmdbProperties.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        return new HttpEntity<>(headers);
    }

    private String addFiltersToUrl(GetRandomMovieRequest request, String url) {
        StringBuilder builder = new StringBuilder(url);
        builder.append("?sort_by=popularity.desc");
        if (request.getGenreIds() != null && !request.getGenreIds().isEmpty()) {
            builder.append("&with_genres=").append(MovieUtils.convertIntegerListToString(request.getGenreIds()));
        }

        if (request.getFromYear() != null) {
            builder.append("&primary_release_date.gte=").append(request.getFromYear()).append("-01-01");
        }

        if (request.getToYear() != null) {
            builder.append("&primary_release_date.lte=").append(request.getToYear()).append("-12-31");
        }

        if (request.getMinimumRating() != null) {
            builder.append("&vote_average.gte=").append(request.getMinimumRating());
        }

        if (request.getIncludeAdult() != null) {
            builder.append("&include_adult=").append(request.getIncludeAdult());
        }
        return builder.toString();
    }
}
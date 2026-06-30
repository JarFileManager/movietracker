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

    public List<TmdbMovie> getPopularMovies() {
        String url = tmdbProperties.getBaseUrl() + "/movie/popular";
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
        if(request.getGenreIds() != null && !request.getGenreIds().isEmpty()) {
            url += "?with_genres=" + MovieUtils.convertIntegerListToString(request.getGenreIds());
        }
        return url;
    }
}
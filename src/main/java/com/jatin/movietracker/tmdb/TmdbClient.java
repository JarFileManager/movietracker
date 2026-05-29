package com.jatin.movietracker.tmdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    private HttpEntity<Void> createEntity(){
        String accessToken = tmdbProperties.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        return new HttpEntity<>(headers);
    }
}
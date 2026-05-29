package com.jatin.movietracker.tmdb;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;



@Component
@RequiredArgsConstructor
public class TmdbClient {

    private final RestTemplate restTemplate;

    private final TmdbProperties tmdbProperties;

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
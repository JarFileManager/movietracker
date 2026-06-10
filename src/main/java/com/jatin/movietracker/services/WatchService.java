package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.requests.MarkWatchedRequest;
import com.jatin.movietracker.dtos.responses.WatchedMovieResponse;
import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.entities.WatchedMovie;
import com.jatin.movietracker.repositories.WatchedMovieRepository;
import com.jatin.movietracker.utils.WatchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WatchService {

    private final UserService userService;
    private final WatchedMovieRepository watchedMovieRepository;

    public WatchedMovieResponse markMovieAsWatched(MarkWatchedRequest request) {

        User user = userService.getCurrentUser();

        Optional<WatchedMovie> existingMovie = watchedMovieRepository.findByUserAndApiMovieId(user, request.getApiMovieId());

        if (existingMovie.isPresent()) {
            WatchedMovie movie = existingMovie.get();
            movie.setWatched(request.getWatched());
            watchedMovieRepository.save(movie);

            return WatchUtils.watchedMovieToWatchedMovieResponseConverter(movie);
        }

        WatchedMovie movie = new WatchedMovie();

        movie.setUser(user);
        movie.setApiMovieId(request.getApiMovieId());
        movie.setWatched(request.getWatched());

        WatchedMovie savedMovie = watchedMovieRepository.save(movie);

        return WatchUtils.watchedMovieToWatchedMovieResponseConverter(savedMovie);
    }

    public List<WatchedMovieResponse> getUserWatchedMovies() {

        User user = userService.getCurrentUser();

        return watchedMovieRepository
                .findByUser(user)
                .stream()
                .map(WatchUtils::watchedMovieToWatchedMovieResponseConverter)
                .toList();
    }

    public void deleteWatchedMovie(Long apiMovieId) {

        User user = userService.getCurrentUser();
        WatchedMovie watchedMovie = watchedMovieRepository.findByUserAndApiMovieId(user, apiMovieId).orElseThrow();

        watchedMovieRepository.delete(watchedMovie);
    }
}
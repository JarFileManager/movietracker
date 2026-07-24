package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.requests.MarkWatchedRequest;
import com.jatin.movietracker.dtos.responses.WatchedMovieResponse;
import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.entities.WatchedMovie;
import com.jatin.movietracker.exceptions.ResourceNotFoundException;
import com.jatin.movietracker.repositories.WatchedMovieRepository;
import com.jatin.movietracker.utils.WatchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            watchedMovieRepository.save(movie);

            return WatchUtils.watchedMovieToWatchedMovieResponseConverter(movie);
        }

        WatchedMovie movie = new WatchedMovie();

        movie.setUser(user);
        movie.setApiMovieId(request.getApiMovieId());
        movie.setMovieTitle(request.getMovieTitle());


        WatchedMovie savedMovie = watchedMovieRepository.save(movie);
        return WatchUtils.watchedMovieToWatchedMovieResponseConverter(savedMovie);
    }

    public Page<WatchedMovieResponse> getUserWatchedMovies(Integer page, Integer size) {
        User user = userService.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);
        Page<WatchedMovie> watchedMovies = watchedMovieRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        return watchedMovies.map(WatchUtils::watchedMovieToWatchedMovieResponseConverter);
    }

    public void deleteWatchedMovie(Long apiMovieId) {

        User user = userService.getCurrentUser();
        WatchedMovie watchedMovie = watchedMovieRepository.findByUserAndApiMovieId(user, apiMovieId).orElseThrow(() -> new ResourceNotFoundException("Movie not found in watched list"));

        watchedMovieRepository.delete(watchedMovie);
    }
}
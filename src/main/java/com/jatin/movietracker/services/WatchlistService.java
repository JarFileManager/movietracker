package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.requests.WatchlistRequest;
import com.jatin.movietracker.dtos.responses.WatchlistResponse;
import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.entities.Watchlist;
import com.jatin.movietracker.repositories.WatchlistRepository;
import com.jatin.movietracker.utils.WatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {
    private final WatchlistRepository watchlistRepository;
    private final UserService userService;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository, UserService userService) {
        this.watchlistRepository = watchlistRepository;
        this.userService = userService;
    }

    public WatchlistResponse addToWatchlist(WatchlistRequest watchlistRequest) {
        User user = userService.getCurrentUser();
        Optional<Watchlist> existingWatchlist = watchlistRepository.findByUserAndApiMovieId(user, watchlistRequest.getApiMovieId());
        if (existingWatchlist.isPresent()) {
            return WatchUtils.watchlistToWatchlistResponseConverter(existingWatchlist.get());
        }

        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        watchlist.setApiMovieId(watchlistRequest.getApiMovieId());
        watchlist.setMovieTitle(watchlistRequest.getMovieTitle());
        watchlistRepository.save(watchlist);

        return WatchUtils.watchlistToWatchlistResponseConverter(watchlist);
    }

    public List<WatchlistResponse> getMyWatchlist() {
        User user = userService.getCurrentUser();
        List<Watchlist> watchlist = watchlistRepository.findByUser(user);
        if (watchlist.isEmpty()) {
            return Collections.emptyList();
        }
        return watchlist.stream()
                .map(WatchUtils::watchlistToWatchlistResponseConverter)
                .toList();
    }

    public String removeFromWatchlist(Integer apiMovieId) {
        User user = userService.getCurrentUser();
        Optional<Watchlist> watchlist = watchlistRepository.findByUserAndApiMovieId(user, apiMovieId);
        watchlist.ifPresent(watchlistRepository::delete);
        return "Movie removed from watchlist";
    }
}

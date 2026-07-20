package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.WatchlistRequest;
import com.jatin.movietracker.dtos.responses.WatchlistResponse;
import com.jatin.movietracker.services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/watchlist")
public class WatchlistController {

    private final WatchlistService watchlistService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<WatchlistResponse>> getWatchlist() {
        List<WatchlistResponse> watchlist = watchlistService.getMyWatchlist();
        return ResponseEntity.ok(watchlist);
    }

    @PostMapping
    public ResponseEntity<WatchlistResponse> addToWatchlist(@RequestBody WatchlistRequest watchlistRequest) {
        return ResponseEntity.ok(watchlistService.addToWatchlist(watchlistRequest));
    }

    @DeleteMapping("/remove/{apiMovieId}")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable Integer apiMovieId) {
        return ResponseEntity.ok(watchlistService.removeFromWatchlist(apiMovieId));
    }
}

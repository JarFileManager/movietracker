package com.jatin.movietracker.repositories;

import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.entities.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WatchlistRepository extends JpaRepository<Watchlist, UUID> {

    List<Watchlist> findByUser(User user);

    Optional<Watchlist> findByUserAndApiMovieId(User user, Integer apiMovieId);
}
package com.jatin.movietracker.repositories;

import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.entities.Watchlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WatchlistRepository extends JpaRepository<Watchlist, UUID> {

    Optional<Watchlist> findByUserAndApiMovieId(User user, Integer apiMovieId);

    Page<Watchlist> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
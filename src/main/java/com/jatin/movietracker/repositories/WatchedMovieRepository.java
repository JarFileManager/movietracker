package com.jatin.movietracker.repositories;

import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.entities.WatchedMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, UUID> {

    Optional<WatchedMovie> findByUserAndApiMovieId(
            User user,
            Long apiMovieId
    );

    Page<WatchedMovie> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}

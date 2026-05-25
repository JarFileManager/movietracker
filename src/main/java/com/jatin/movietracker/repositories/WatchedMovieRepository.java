package com.jatin.movietracker.repositories;

import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.entities.WatchedMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, UUID> {
    List<WatchedMovie> findByUser(User user);

    Optional<WatchedMovie> findByUserAndApiMovieId(
            User user,
            Long apiMovieId
    );
}

package com.jatin.movietracker.repositories;

import com.jatin.movietracker.entities.Review;
import com.jatin.movietracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findByUserAndApiMovieId(
            User user,
            Long apiMovieId
    );

    List<Review> findByApiMovieId(Long apiMovieId);
}

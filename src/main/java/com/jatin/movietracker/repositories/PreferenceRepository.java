package com.jatin.movietracker.repositories;

import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.entities.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PreferenceRepository extends JpaRepository<UserPreference, UUID> {
    Optional<UserPreference> findByUser(User user);
}

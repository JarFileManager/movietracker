package com.jatin.movietracker.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "watched_movies",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "api_movie_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchedMovie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "api_movie_id", nullable = false)
    private Long apiMovieId;

    private String movieTitle;

}

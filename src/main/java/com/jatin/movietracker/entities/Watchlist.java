package com.jatin.movietracker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(
        name = "watchlist",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "api_movie_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Watchlist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "api_movie_id", nullable = false)
    private Integer apiMovieId;

    @Column(name = "movie_title", nullable = false)
    private String movieTitle;
}

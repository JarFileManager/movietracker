package com.jatin.movietracker.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "api_movie_id"}
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(name = "api_movie_id", nullable = false)
    private Long apiMovieId;

    @Min(1)
    @Max(5)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

}

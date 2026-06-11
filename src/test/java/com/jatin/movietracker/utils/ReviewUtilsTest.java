package com.jatin.movietracker.utils;

import com.jatin.movietracker.dtos.responses.ReviewResponse;
import com.jatin.movietracker.entities.Review;
import com.jatin.movietracker.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReviewUtilsTest {

    @Test
    void reviewToReviewResponseConverter_ShouldConvertReviewToReviewResponse() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");

        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setApiMovieId(123L);
        review.setRating(4);
        review.setComment("Great movie!");
        review.setUser(user);
        review.setCreatedAt(LocalDateTime.now());

        // Act
        ReviewResponse response = ReviewUtils.reviewToReviewResponseConverter(review);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(review.getId());
        assertThat(response.getApiMovieId()).isEqualTo(review.getApiMovieId());
        assertThat(response.getRating()).isEqualTo(review.getRating());
        assertThat(response.getComment()).isEqualTo(review.getComment());
        assertThat(response.getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getCreatedAt()).isEqualTo(review.getCreatedAt());
    }
}

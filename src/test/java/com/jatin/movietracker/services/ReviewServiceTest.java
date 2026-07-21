package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.requests.CreateReviewRequest;
import com.jatin.movietracker.dtos.responses.ReviewResponse;
import com.jatin.movietracker.entities.Review;
import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReviewService reviewService;

    private User testUser;
    private Review testReview;
    private CreateReviewRequest reviewRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setUsername("testuser");

        testReview = new Review();
        testReview.setId(UUID.randomUUID());
        testReview.setUser(testUser);
        testReview.setApiMovieId(123L);
        testReview.setRating(4);
        testReview.setComment("Good movie");

        reviewRequest = new CreateReviewRequest();
        reviewRequest.setApiMovieId(123L);
        reviewRequest.setRating(5);
        reviewRequest.setComment("Updated comment");
        }


    @Test
    void addReview_NewReview_ShouldCreateAndReturnReview() {
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(reviewRepository.findByUserAndApiMovieId(testUser, 123L)).thenReturn(Optional.empty());
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReviewResponse response = reviewService.addReview(reviewRequest);

        assertThat(response).isNotNull();
        assertThat(response.getRating()).isEqualTo(5);
        assertThat(response.getComment()).isEqualTo("Updated comment");
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void addReview_ExistingReview_ShouldUpdateAndReturnReview() {
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(reviewRepository.findByUserAndApiMovieId(testUser, 123L)).thenReturn(Optional.of(testReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        ReviewResponse response = reviewService.addReview(reviewRequest);

        assertThat(response).isNotNull();
        assertThat(testReview.getRating()).isEqualTo(5);
        assertThat(testReview.getComment()).isEqualTo("Updated comment");
        verify(reviewRepository).save(testReview);
    }

    @Test
    void getMyReview_Found_ShouldReturnReview() {
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(reviewRepository.findByUserAndApiMovieId(testUser, 123L)).thenReturn(Optional.of(testReview));

        ReviewResponse response = reviewService.getMyReview(123L);

        assertThat(response).isNotNull();
        assertThat(response.getApiMovieId()).isEqualTo(123L);
    }

    @Test
    void getMyReview_NotFound_ShouldReturnNull() {
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(reviewRepository.findByUserAndApiMovieId(testUser, 123L)).thenReturn(Optional.empty());

        ReviewResponse response = reviewService.getMyReview(123L);

        assertThat(response).isNull();
    }

    @Test
    void getMyMovieReviews_NotEmpty_ShouldReturnPage() {
        when(userService.getCurrentUser()).thenReturn(testUser);
        Page<Review> reviewPage = new PageImpl<>(List.of(testReview), PageRequest.of(0, 10), 1);
        when(reviewRepository.findByUserOrderByCreatedAtDesc(eq(testUser), any())).thenReturn(reviewPage);

        Page<ReviewResponse> responses = reviewService.getMyMovieReviews(0, 10);

        assertThat(responses).isNotEmpty();
        assertThat(responses.getContent()).hasSize(1);
        assertThat(responses.getContent().get(0).getApiMovieId()).isEqualTo(123L);
        assertThat(responses.getTotalElements()).isEqualTo(1);
    }

    @Test
    void getMyMovieReviews_Empty_ShouldReturnEmptyPage() {
        when(userService.getCurrentUser()).thenReturn(testUser);
        Page<Review> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
        when(reviewRepository.findByUserOrderByCreatedAtDesc(eq(testUser), any())).thenReturn(emptyPage);

        Page<ReviewResponse> responses = reviewService.getMyMovieReviews(0, 10);

        assertThat(responses.getContent()).isEmpty();
        assertThat(responses.getTotalElements()).isEqualTo(0);
    }

    @Test
    void updateReview_Success_ShouldUpdateAndReturnReview() {
        UUID reviewId = testReview.getId();
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(testReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        ReviewResponse response = reviewService.updateReview(reviewId, reviewRequest);

        assertThat(response).isNotNull();
        assertThat(testReview.getRating()).isEqualTo(5);
        verify(reviewRepository).save(testReview);
    }

    @Test
    void updateReview_Unauthorized_ShouldThrowException() {
        UUID reviewId = testReview.getId();
        User anotherUser = new User();
        anotherUser.setEmail("other@example.com");

        when(userService.getCurrentUser()).thenReturn(anotherUser);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(testReview));

        assertThatThrownBy(() -> reviewService.updateReview(reviewId, reviewRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("You are not authorized to perform this action.");
    }

    @Test
    void deleteReview_Success_ShouldDeleteReview() {
        UUID reviewId = testReview.getId();
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(testReview));

        reviewService.deleteReview(reviewId);

        verify(reviewRepository).delete(testReview);
    }

    @Test
    void deleteReview_Unauthorized_ShouldThrowException() {
        UUID reviewId = testReview.getId();
        User anotherUser = new User();
        anotherUser.setEmail("other@example.com");

        when(userService.getCurrentUser()).thenReturn(anotherUser);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(testReview));

        assertThatThrownBy(() -> reviewService.deleteReview(reviewId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("You are not authorized to perform this action.");
    }
}

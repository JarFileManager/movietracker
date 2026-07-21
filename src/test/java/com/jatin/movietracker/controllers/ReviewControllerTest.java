package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.CreateReviewRequest;
import com.jatin.movietracker.dtos.responses.ReviewResponse;
import com.jatin.movietracker.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @Test
    void addReview_ShouldReturnOk() {
        CreateReviewRequest request = new CreateReviewRequest();
        ReviewResponse response = new ReviewResponse();
        when(reviewService.addReview(any(CreateReviewRequest.class))).thenReturn(response);

        ResponseEntity<ReviewResponse> result = reviewController.addReview(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void getMyReview_Found_ShouldReturnOk() {
        Long apiMovieId = 123L;
        ReviewResponse response = new ReviewResponse();
        when(reviewService.getMyReview(apiMovieId)).thenReturn(response);

        ResponseEntity<ReviewResponse> result = reviewController.getMyReview(apiMovieId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void getMyReview_NotFound_ShouldReturnNotFound() {
        Long apiMovieId = 123L;
        when(reviewService.getMyReview(apiMovieId)).thenReturn(null);

        ResponseEntity<ReviewResponse> result = reviewController.getMyReview(apiMovieId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getMyReviews_ShouldReturnOk() {
        ReviewResponse response = new ReviewResponse();
        Page<ReviewResponse> reviewPage = new PageImpl<>(List.of(response), PageRequest.of(0, 10), 1);
        when(reviewService.getMyMovieReviews(0, 10)).thenReturn(reviewPage);

        ResponseEntity<Page<ReviewResponse>> result = reviewController.getMyReviews(0, 10);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).hasSize(1);
        assertThat(result.getBody().getTotalElements()).isEqualTo(1);
    }

    @Test
    void updateReview_ShouldReturnOk() {
        UUID reviewId = UUID.randomUUID();
        CreateReviewRequest request = new CreateReviewRequest();
        ReviewResponse response = new ReviewResponse();
        when(reviewService.updateReview(eq(reviewId), any(CreateReviewRequest.class))).thenReturn(response);

        ResponseEntity<ReviewResponse> result = reviewController.updateReview(reviewId, request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void deleteReview_ShouldReturnOk() {
        UUID reviewId = UUID.randomUUID();
        doNothing().when(reviewService).deleteReview(reviewId);

        ResponseEntity<String> result = reviewController.deleteReview(reviewId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).contains("deleted");
        verify(reviewService).deleteReview(reviewId);
    }
}

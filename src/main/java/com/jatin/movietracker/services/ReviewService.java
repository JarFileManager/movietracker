package com.jatin.movietracker.services;

import com.jatin.movietracker.dtos.requests.CreateReviewRequest;
import com.jatin.movietracker.dtos.responses.ReviewResponse;
import com.jatin.movietracker.entities.Review;
import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.repositories.ReviewRepository;
import com.jatin.movietracker.utils.ReviewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }

    public ReviewResponse addReview(CreateReviewRequest request){

        User user = userService.getCurrentUser();
        Optional<Review> existingReview = reviewRepository.findByUserAndApiMovieId(user, request.getApiMovieId());

        Review review;
        if (existingReview.isPresent()) {
            review = existingReview.get();
        } else {
            review = new Review();
            review.setUser(user);
            review.setApiMovieId(request.getApiMovieId());
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        Review saved = reviewRepository.save(review);

        return ReviewUtils.reviewToReviewResponseConverter(saved);
    }

    public ReviewResponse getMyReview(Long apiMovieId){
        User user = userService.getCurrentUser();
        Optional<Review> existingReview = reviewRepository.findByUserAndApiMovieId(user, apiMovieId);
        if(existingReview.isPresent()){
            Review review = existingReview.get();
            return ReviewUtils.reviewToReviewResponseConverter(review);
        }

        return null;
    }

    public List<ReviewResponse> getMyMovieReviews(){
        User user = userService.getCurrentUser();
        List<Review> reviews = reviewRepository.findByUser(user);
        if(reviews.isEmpty()){
            return Collections.emptyList();
        }

        return reviews.stream().map(ReviewUtils::reviewToReviewResponseConverter).collect(Collectors.toList());
    }

    public ReviewResponse updateReview(UUID reviewId, CreateReviewRequest createReviewRequest){
        User user = userService.getCurrentUser();
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        if (!review.getUser().getEmail().equals(user.getEmail())) {
            throw new RuntimeException("Unauthorized");
        }

        review.setRating(createReviewRequest.getRating());
        review.setComment(createReviewRequest.getComment());
        Review saved = reviewRepository.save(review);
        return ReviewUtils.reviewToReviewResponseConverter(saved);
    }

    public void deleteReview(UUID reviewId){
        User user = userService.getCurrentUser();
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        if (!review.getUser().getEmail().equals(user.getEmail())) {
            throw new RuntimeException("Unauthorized");
        }
        reviewRepository.delete(review);
    }
}

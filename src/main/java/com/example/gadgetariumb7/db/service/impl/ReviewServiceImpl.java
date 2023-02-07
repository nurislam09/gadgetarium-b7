package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Review;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.repository.ReviewRepository;
import com.example.gadgetariumb7.db.service.ReviewService;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.ReviewResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.dto.response.UserResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    @Override
    public List<ReviewResponse> getAll(String statusOfReviews) {
        List<ReviewResponse> all = reviewRepository.getAllByAdmin();
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (ReviewResponse reviewResponse : all) {
            Optional<Review> reviewOptional = reviewRepository.findById(reviewResponse.getId());
            if (reviewOptional.isPresent()) {
                Review review = reviewOptional.get();
                reviewResponse.setProductImage(reviewRepository.getFirstImage(review.getProduct().getId()));
            }
            User user = reviewRepository.getUserReview(reviewResponse.getId());
            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getFirstName(), user.getLastName(),
                    user.getEmail(),
                    user.getImage()
            );
            reviewResponse.setUserResponse(userResponse);
            reviewResponses.add(reviewResponse);
        }
        switch (statusOfReviews) {
            case "Все отзывы" -> {
                return reviewResponses;
            }
            case "Неотвеченные" -> {
                return reviewResponses.stream().filter(p -> p.isStatusOfResponse() == false).toList();
            }
            case "Отвеченные" -> {
                return reviewResponses.stream().filter(p -> p.isStatusOfResponse() == true).toList();
            }
        }
        return reviewResponses;
    }

    @Override
    public Map<Integer, Integer> countReviewsGrade() {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (int i = 1; i < 6; i++) {
            counts.put(i, reviewRepository.countReviewByProductGrade((byte) i));
        }
        return counts;
    }

    @Override
    public SimpleResponse update(Long id, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Not found")));
        review.setResponseOfReview(reviewRequest.getResponseOfReview());
        review.setStatusOfResponse(review.getResponseOfReview().length() != 0);
        reviewRepository.save(review);
        return new SimpleResponse("Answer successfully saved", "ok");
    }

    @Override
    public SimpleResponse deleteReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("not found!")));
        reviewRepository.delete(review);
        return new SimpleResponse("deleted", "ok");
    }
}

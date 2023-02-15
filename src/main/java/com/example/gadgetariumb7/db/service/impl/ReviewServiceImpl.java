package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.Review;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.repository.ReviewRepository;
import com.example.gadgetariumb7.db.service.ReviewService;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.*;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResponses getAll(String statusOfReview) {
        ReviewResponses reviewResponses = new ReviewResponses();
        List<ReviewResponse> allReview = reviewRepository.getAllByAdmin();
        List<ReviewResponse> answered = reviewRepository.getAllReviewByStatusTrue();
        List<ReviewResponse> unanswered = reviewRepository.getAllReviewByStatusOfResponseFalse();
        duplicateCode(allReview);
        duplicateCode(answered);
        duplicateCode(unanswered);
        if (statusOfReview.equals("Отвеченные")) {
            reviewResponses.setReviewResponses(answered);
        } else if (statusOfReview.equals("Неотвеченные")) {
            reviewResponses.setReviewResponses(unanswered);
        } else if (statusOfReview.equals("Все отзывы")) {
            reviewResponses.setReviewResponses(allReview);
        }
        reviewResponses.setCountGrade(countReviewsGrade());
        reviewResponses.setCountReviewUnAnswered(reviewRepository.countReviewUnAnswered());
        return reviewResponses;
    }

    private void duplicateCode(List<ReviewResponse> answered) {
        for (ReviewResponse r : answered) {
            User user = reviewRepository.getUserReview(r.getId());
            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getFirstName(), user.getLastName(),
                    user.getEmail(),
                    user.getImage()
            );
            Product product = reviewRepository.getProductReview(r.getId());
            ProductReviewResponse productReviewResponse = new ProductReviewResponse(product);
            productReviewResponse.setBrandResponse(new BrandResponse(product));
            r.setProductReviewResponse(productReviewResponse);
            r.setUserResponse(userResponse);
        }
    }

    @Override
    public Map<Integer, Integer> countReviewsGrade() {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (double i = 1; i < 6; i++) {
            counts.put((int)i, reviewRepository.countReviewByProductGrade(i));
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

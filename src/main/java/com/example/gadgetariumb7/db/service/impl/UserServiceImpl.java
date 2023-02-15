package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.Review;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.repository.ReviewRepository;
import com.example.gadgetariumb7.db.repository.UserRepository;
import com.example.gadgetariumb7.db.service.UserService;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.BadRequestException;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public SimpleResponse addReview(ReviewRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new NotFoundException("Product not found"));
        User user = getAuthenticateUser();

        if (!user.getOrderHistoryList().contains(product)){
            throw new BadRequestException("This customer did not purchase this product");
        }

        if (product.getUsersReviews() != null) {
            for (Review r : product.getUsersReviews()) {
                if (r.getUser().getId().equals(user.getId())) {
                    throw new BadRequestException("User has already added a review for this product");
                }
            }
        }

        Review review = new Review(request.getProductGrade(), request.getReviewComment(), user, product);
        review.setReviewTime(LocalDateTime.now());
        review.setStatusOfResponse(false);

        if (request.getImages() != null) {
            review.setImages(request.getImages());
        }

        if (product.getUsersReviews() != null) {
            product.getUsersReviews().add(review);
            double rating = product.getUsersReviews().stream().mapToDouble(Review::getProductGrade).average().getAsDouble();
            product.setProductRating(Math.round(rating * 100.0) / 100.0);
        } else {
            product.setProductRating(request.getProductGrade());
            product.setUsersReviews(Arrays.asList(review));
        }

        if (user.getUserReviews() != null)
            user.getUserReviews().add(review);
        else
            user.setUserReviews(Arrays.asList(review));

        reviewRepository.save(review);
        productRepository.save(product);
        userRepository.save(user);
        return new SimpleResponse("Review successfully saved", "ok");
    }
}

package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Review;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.repository.ReviewRepository;
import com.example.gadgetariumb7.dto.response.ReviewResponse;
import com.example.gadgetariumb7.dto.response.ReviewResponses;
import com.example.gadgetariumb7.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public ReviewResponse convertToResponse(Review review){
        ReviewResponse reviewResponse = new ReviewResponse(review.getId(), review.getProduct().getProductName(), review.getResponseOfReview(), review.getProduct().getProductImages(), review.getProductGrade(), review.getProduct().getProductVendorCode(),review.isStatusOfResponse());
//        reviewResponse.setProductImage(reviewRepository.getFirstImage(reviewResponse.getId()));
        User user = reviewRepository.getUserReview(reviewResponse.getId());
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getFirstName(), user.getLastName(),
                user.getEmail(),
                user.getImage()
        );
        reviewResponse.setUserResponse(userResponse);
      return   reviewResponse;
    }

    public List<ReviewResponse> getAllReviewResponses() {
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (Review review : reviewRepository.findAll()) {
            reviewResponses.add(convertToResponse(review));
        }
        return reviewResponses;
    }

    public List<ReviewResponse> getAll(){
        return reviewRepository.getAllByAdmin();
    }




//    public void save(ReviewRequest reviewRequest) {
//        Review review = new Review(reviewRequest);
//        review.setStatusOfResponse(true);
//       reviewRepository.save(review);
//
//    }

    List<ReviewResponse> answered() {
        List<ReviewResponse> reviewResponses = getAllReviewResponses().stream().filter(p -> p.isStatusOfResponse() == true).toList();
        return reviewResponses;
    }

    List<ReviewResponse> unAnswered() {
        List<ReviewResponse> reviewResponses = getAllReviewResponses().stream().filter(p -> p.isStatusOfResponse() == false).toList();
        return reviewResponses;
    }


//    public ReviewResponse getById(Long id) {
//        Review review = reviewRepository.findById(id).orElseThrow(
//                () -> new NotFoundException(String.format("not found!"))
//        );
//
//        ReviewResponse response = new ReviewResponse(review.getId(), review.getProduct().getProductImages().get(0), review.getProduct().getProductName(),
//                review.getResponseOfReview(), review.getProduct().getProductImages(), review.getProductGrade(), review.getProduct().getProductVendorCode());
//
//        UserResponse userResponse = new UserResponse(
//                review.getUser().getId(),
//                review.getUser().getFirstName(), review.getUser().getLastName(),
//                review.getUser().getEmail(),
//                review.getUser().getImage()
//        );
//        response.setUserResponse(userResponse);
//        return response;
//    }

//    public void deleteReviewById(Long id) {
//        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("not found!")));
//        review.setUser(null);
//        review.setProduct(null);
//        reviewRepository.delete(review);
//    }
}

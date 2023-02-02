package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Review;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.repository.ReviewRepository;
import com.example.gadgetariumb7.db.repository.UserRepository;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.ReviewResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.dto.response.UserResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


//    public ReviewResponse convertToResponse(Review review){
//        ReviewResponse reviewResponse = new ReviewResponse(review.getId(), review.getProduct().getProductName(), review.getResponseOfReview(), review.getProduct().getProductImages(), review.getProductGrade(), review.getProduct().getProductVendorCode(),review.isStatusOfResponse());
////        reviewResponse.setProductImage(reviewRepository.getFirstImage(reviewResponse.getId()));
//        User user = reviewRepository.getUserReview(reviewResponse.getId());
//        UserResponse userResponse = new UserResponse(
//                user.getId(),
//                user.getFirstName(), user.getLastName(),
//                user.getEmail(),
//                user.getImage()
//        );
//        reviewResponse.setUserResponse(userResponse);
//      return   reviewResponse;
//    }
//
//    public List<ReviewResponse> getAllReviewResponses() {
//        List<ReviewResponse> reviewResponses = new ArrayList<>();
//        for (Review review : reviewRepository.findAll()) {
//            reviewResponses.add(convertToResponse(review));
//        }
//        return reviewResponses;
//    }

    public List<ReviewResponse> getAll(){
        List<ReviewResponse> all = reviewRepository.getAllByAdmin();
        for (ReviewResponse r :all) {
            r.setUserResponse(new UserResponse(new User()));

        }
        return reviewRepository.getAllByAdmin();
    }




    public SimpleResponse save(ReviewRequest reviewRequest) {
        Review review = new Review(reviewRequest);
        review.setStatusOfResponse(true);
       reviewRepository.save(review);
        return new SimpleResponse("saved","OK");
    }

    public ReviewResponse update(Long id,ReviewRequest reviewRequest){
    if (reviewRequest==null){
        return null;
    }

//    ReviewResponse response = getById(id);
//    response.setResponseOfReview(reviewRequest.getResponseOfReview());


        Review review = reviewRepository.getById(id);
        String responseOfReview = reviewRequest.getResponseOfReview();
        review.setResponseOfReview(responseOfReview);
        reviewRepository.save(review);

        return new ReviewResponse(review.getId(),review.isStatusOfResponse(),review.getResponseOfReview(),review.getProductGrade(),review.getProduct().getProductVendorCode(),
                review.getProduct().getProductName());
//        return response;
    }

//    List<ReviewResponse> answered() {
//        List<ReviewResponse> reviewResponses = getAllReviewResponses().stream().filter(p -> p.isStatusOfResponse() == true).toList();
//        return reviewResponses;
//    }
//
//    List<ReviewResponse> unAnswered() {
//        List<ReviewResponse> reviewResponses = getAllReviewResponses().stream().filter(p -> p.isStatusOfResponse() == false).toList();
//        return reviewResponses;
//    }


    public ReviewResponse getById(Long id) {
        ReviewResponse reviewById = reviewRepository.getReviewById(id);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("not found"));
        reviewById.setUserResponse(new UserResponse(user));
//        UserResponse user = userRepository.getUser();
//        reviewById.setUserResponse(user);
        return reviewById;
    }

    public SimpleResponse deleteReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("not found!")));
//        review.setUser(null);
//        review.setProduct(null);
//        Review review = reviewRepository.deleteReview(id);
        reviewRepository.delete(review);
        return new SimpleResponse("deleted","OK");
    }
}

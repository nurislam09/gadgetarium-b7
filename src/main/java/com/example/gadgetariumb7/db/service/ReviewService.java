package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.Review;
import com.example.gadgetariumb7.db.repository.ReviewRepository;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.ReviewResponse;
import com.example.gadgetariumb7.dto.response.UserResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

   private final  ReviewRepository reviewRepository;



   public void save(ReviewRequest reviewRequest){
      Review review=new Review(reviewRequest);
      review.setStatusOfResponse(true);
      reviewRepository.save(review);
   }

   public ReviewResponse  updateReview(ReviewRequest reviewRequest){
      return new ReviewResponse(reviewRequest);
   }


   public List<ReviewResponse> getAll(){
      return reviewRepository.getAllReviews();
   }


   public ReviewResponse getById(Long id){
      Review review = reviewRepository.findById(id).orElseThrow(
              () -> new NotFoundException(String.format("not found!"))
      );

      ReviewResponse response =new ReviewResponse(review.getId(),review.getProduct().getProductImages().get(0),review.getProduct().getProductName(),
              review.getResponseOfReview(),review.getProduct().getProductImages(),review.getProductGrade(),review.getProduct().getProductVendorCode());

      UserResponse userResponse=new UserResponse(
              review.getUser().getId(),
              review.getUser().getFirstName(),review.getUser().getLastName(),
              review.getUser().getEmail(),
              review.getUser().getImage()
      );
      response.setUserResponse(userResponse);
      return response;
   }

   public void deleteReviewById(Long id){
      Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("not found!")));
      review.setUser(null);
      review.setProduct(null);
      reviewRepository.delete(review);
   }

  List <ReviewResponse> answered (){


     List<ReviewResponse> allReviews = reviewRepository.getAllReviews();
     allReviews.forEach(i -> {
        Review r =reviewRepository.getById(i.getId());
        i.setProductImage(r.getProduct().getProductImages().get(0));
     });

  }
}

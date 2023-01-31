package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ReviewService;
import com.example.gadgetariumb7.dto.response.ReviewResponse;
import com.example.gadgetariumb7.dto.response.ReviewResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reviews")
@Tag(name="review")
public class ReviewController {


    private final ReviewService reviewService;

    @GetMapping()
    @Operation(summary = "reviews",description = "get all reviews")
   public List<ReviewResponse> getAll(){
        return reviewService.getAll();
   }



//   @GetMapping("/{id}")
//   @Operation(summary = "review",description = "get review by id")
//   public ReviewResponse getById(@PathVariable Long id){
//        return reviewService.getById(id);
//   }
//
//   @DeleteMapping
//   @Operation(summary = "delete review",description = "delete review by id")
//   public void deleteById(@PathVariable Long id){
//        reviewService.deleteReviewById(id);
//   }
//
//
//   @PostMapping
//   @Operation(summary = "save",description = "save review")
//   public  void save(ReviewRequest reviewRequest){
//        reviewService.save(reviewRequest);
//   }

//   @PatchMapping
//   @Operation(summary ="update ",description = "update comment")
//   public ReviewResponse updateReview(ReviewRequest reviewRequest){
//      return   reviewService.updateReview(reviewRequest);
//   }




}

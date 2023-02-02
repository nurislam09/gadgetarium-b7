package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ReviewService;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.ReviewResponse;
import com.example.gadgetariumb7.dto.response.ReviewResponses;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
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



   @GetMapping("/{id}")
   @Operation(summary = "review",description = "get review by id")
   public ReviewResponse getById(@PathVariable Long id){
        return reviewService.getById(id);
   }

   @PostMapping("/{id}")
   @Operation(summary = "delete review",description = "delete review by id")
   public SimpleResponse deleteById(@PathVariable Long id){
      return   reviewService.deleteReviewById(id);
   }


   @PostMapping
   @Operation(summary = "save",description = "save review")
   public  SimpleResponse save(ReviewRequest reviewRequest){
      return   reviewService.save(reviewRequest);
   }

   @PutMapping("/{id}")
   @Operation(summary ="update ",description = "update comment")
   public ReviewResponse updateReview(@PathVariable Long id,ReviewRequest reviewRequest){
      return   reviewService.update(id,reviewRequest);
   }




}

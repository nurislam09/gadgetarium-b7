package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.impl.ReviewServiceImpl;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.ReviewResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reviews")
@Tag(name="Review API")
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    @GetMapping()
    @Operation(summary = "reviews",description = "get all reviews")
   public List<ReviewResponse> getAll(@RequestParam String statusOfReviews){
        return reviewService.getAll(statusOfReviews);
   }
   @GetMapping("/countReviewsByGrade")
   @Operation(summary = "count reviews by grade",description = "first value is grade, second value is count reviews")
   public Map<Integer,Integer> countReview(){
        return reviewService.countReviewsGrade();
    }

   @DeleteMapping("/{id}")
   @Operation(summary = "delete review",description = "delete review by id")
   public SimpleResponse deleteById(@PathVariable Long id){
      return   reviewService.deleteReviewById(id);
   }

   @PutMapping("/{id}")
   @Operation(summary ="answer and update ",description = "answer saved")
   public SimpleResponse updateReview(@PathVariable Long id,ReviewRequest reviewRequest){
      return  reviewService.update(id,reviewRequest);
   }
}

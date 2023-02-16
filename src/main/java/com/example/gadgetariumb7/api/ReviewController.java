package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.impl.ReviewServiceImpl;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.ReviewResponses;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Review API")
public class ReviewController {
    private final ReviewServiceImpl reviewService;

    @GetMapping()
    @Operation(summary = "Reviews", description = "Get all reviews")
    @PreAuthorize("hasAuthority('Admin')")
    public ReviewResponses getAll(@RequestParam String statusOfReviews) {
        return reviewService.getAll(statusOfReviews);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review", description = "Delete review by id")
    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse deleteById(@PathVariable Long id) {
        return reviewService.deleteReviewById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Answer and update ", description = "Answer saved")
    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse updateReview(@PathVariable Long id, ReviewRequest reviewRequest) {
        return reviewService.update(id, reviewRequest);
    }
}

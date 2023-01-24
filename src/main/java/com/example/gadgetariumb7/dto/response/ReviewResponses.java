package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.dto.request.ReviewRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ReviewResponses {
    private Long id;
    private String productImage;
    private String name;
    private String responseOfReview;
    private List<String> photo;
    private byte productGrade;
    private UserResponse userResponse;

    public ReviewResponses(Long id, String productImage, String name, String responseOfReview, List<String> photo, byte productGrade) {
        this.id = id;
        this.productImage = productImage;
        this.name = name;
        this.responseOfReview = responseOfReview;
        this.photo = photo;
        this.productGrade = productGrade;
    }

    public ReviewResponses(ReviewRequest responseOfReview) {
        this.responseOfReview = responseOfReview.getResponseOfReview();
    }
}

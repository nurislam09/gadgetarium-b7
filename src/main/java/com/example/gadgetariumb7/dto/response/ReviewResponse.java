
package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.Review;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponse {
    private Long id;
    private String productImage;
    private String name;
    private String responseOfReview;
    private List<String> photo;
    private byte productGrade;
    private int productVendorCode;
    private boolean statusOfResponse;

    private UserResponse userResponse;
    public ReviewResponse(Long id, boolean statusOfResponse, String responseOfReview, byte productGrade, int productVendorCode, String name) {
        this.id = id;
        this.statusOfResponse = statusOfResponse;
        this.responseOfReview = responseOfReview;
        this.productGrade = productGrade;
        this.productVendorCode = productVendorCode;
        this.name = name;

    }


    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.productImage = review.getProduct().getProductImages().get(0);
        this.name = review.getProduct().getProductName();
        this.responseOfReview = review.getResponseOfReview();
        this.photo = review.getProduct().getProductImages();
        this.productGrade = review.getProductGrade();
        this.productVendorCode = review.getProduct().getProductVendorCode();
        this.statusOfResponse = review.isStatusOfResponse();
    }


}

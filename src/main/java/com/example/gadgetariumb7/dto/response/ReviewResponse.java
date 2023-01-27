
package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.entity.Product;
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
    private UserResponse userResponse;
    private int productVendorCode;
    private boolean statusOfResponse;


    public ReviewResponse(Long id, String productImage, String name, String responseOfReview, List<String> photo, byte productGrade,int productVendorCode) {
        this.id = id;
        this.productImage = productImage;
        this.name = name;
        this.responseOfReview = responseOfReview;
        this.photo = photo;
        this.productGrade = productGrade;
        this.productVendorCode=productVendorCode;
    }

    public ReviewResponse(Long id, String name, String responseOfReview, List<String> photo, byte productGrade, int productVendorCode) {
        this.id = id;
        this.name = name;
        this.responseOfReview = responseOfReview;
        this.photo = photo;
        this.productGrade = productGrade;
        this.productVendorCode = productVendorCode;
    }

    public ReviewResponse(ReviewRequest reviewRequest) {
        this.id=getId();
        this.responseOfReview = reviewRequest.getResponseOfReview();
    }

    //    public ReviewResponse(Product product) {
//        this.photo =product.getProductImages();
//        this.name=product.getProductName();
//        this.productImage= photo.get(0);
//    }







}

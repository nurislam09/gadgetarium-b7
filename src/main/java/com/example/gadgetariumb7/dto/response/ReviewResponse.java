
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

    public ReviewResponse(Long id, boolean statusOfResponse, String responseOfReview, byte productGrade, int productVendorCode, String name) {
        this.id = id;
        this.statusOfResponse = statusOfResponse;
        this.responseOfReview = responseOfReview;
        this.productGrade = productGrade;
        this.productVendorCode = productVendorCode;
        this.name = name;

    }

    //    public ReviewResponse(Long id, String productImage, String name, String responseOfReview, List<String> photo, byte productGrade,
//                          UserResponse userResponse, int productVendorCode, boolean statusOfResponse) {
//        this.id = id;
//        this.productImage = productImage;
//        this.name = name;
//        this.responseOfReview = responseOfReview;
//        this.photo = photo;
//        this.productGrade = productGrade;
//        this.userResponse = userResponse;
//        this.productVendorCode = productVendorCode;
//        this.statusOfResponse = statusOfResponse;
//    }
//
//    public ReviewResponse(Long id, String productImage, String name, String responseOfReview, List<String> photo, byte productGrade, int productVendorCode) {
//        this.id = id;
//        this.productImage = productImage;
//        this.name = name;
//        this.responseOfReview = responseOfReview;
//        this.photo = photo;
//        this.productGrade = productGrade;
//        this.productVendorCode = productVendorCode;
//    }

    public ReviewResponse(Long id, String name, String responseOfReview, List<String> photo, byte productGrade, int productVendorCode, boolean statusOfResponse) {
        this.id = id;
        this.name = name;
        this.responseOfReview = responseOfReview;
        this.photo = photo;
        this.productGrade = productGrade;
        this.productVendorCode = productVendorCode;
        this.statusOfResponse = statusOfResponse;

    }

}

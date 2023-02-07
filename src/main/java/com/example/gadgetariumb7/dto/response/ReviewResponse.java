
package com.example.gadgetariumb7.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponse {
    private Long id;

    private String productImage;
    private String name;

    private String userReview;
    private String responseOfReview;

    private List<String> photo;
    private byte productGrade;
    private int productVendorCode;
    @JsonIgnore
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
}

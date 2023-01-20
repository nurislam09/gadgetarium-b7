
package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private List<String> photo;
    private String name;
    private String responseOfReview;
    private byte productGrade;
    private UserResponse userResponse;

    public ReviewResponse(Product product) {
        this.id = product.getId();
        this.photo =product.getProductImages();
        this.name=product.getProductName();
    }



}

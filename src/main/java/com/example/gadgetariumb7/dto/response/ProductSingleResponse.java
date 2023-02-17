package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.entity.Subproduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSingleResponse {
    private String productName;
    private int productCount;
    private String productImage;
    private Long productVendorCode;
    private String CategoryName;
    private String subCategoryName;
    private int countOfReviews;
    private int productPrice;
    private int discountPrice;
    private Byte productRating;
    private String color;
    private boolean isFavorite;
    private String PDF;
    private String videoReview;
    private List<Subproduct> subproducts;
}

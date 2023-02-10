package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCardResponse {
    @JsonIgnore
    private Long productId;
    private String productImage;
    private String productName;
    private int count;
    private int productPrice;
    private int discountPrice;
    private ProductStatus productStatus;
    private Byte productRating;
    private int countOfReview;
    private boolean isFavorite;
    private boolean isCompared;

    public ProductCardResponse(Long productId, String productImage, String productName, int count, int productPrice, ProductStatus productStatus, Byte productRating) {
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.count = count;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productRating = productRating;
    }
}

package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.enums.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCardResponse {
    private Long productId;

    private String productImage;
    private String productName;
    private int count;
    private int productPrice;
    private int discountPrice;
    private ProductStatus productStatus;
    private Byte productRating;
    private int countFeedback;

    public ProductCardResponse(Long productId, String productName, int count, int productPrice, ProductStatus productStatus, Byte productRating) {
        this.productId = productId;
        this.productName = productName;
        this.count = count;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productRating = productRating;
    }

    public ProductCardResponse(Long productId, String productImage, String productName, int count, int productPrice, int discountPrice, ProductStatus productStatus, Byte productRating, int countFeedback) {
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.count = count;
        this.productPrice = productPrice;
        this.discountPrice = discountPrice;
        this.productStatus = productStatus;
        this.productRating = productRating;
        this.countFeedback = countFeedback;
    }
}

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
    private int currentPrice;
    private int discountPrice;
    private ProductStatus productStatus;
    private Byte productRating;

    public ProductCardResponse(Long productId, String productName, int count, int currentPrice,int productPrice, ProductStatus productStatus, Byte productRating) {
        this.productId = productId;
        this.productName = productName;
        this.count = count;
        this.productPrice = productPrice;
        this.currentPrice= currentPrice;
        this.productStatus = productStatus;
        this.productRating = productRating;
    }

}

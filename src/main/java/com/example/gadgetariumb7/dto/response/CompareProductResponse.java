package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.entity.Product;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompareProductResponse {
    private String productName;
    private String image;
    private int price;

    public CompareProductResponse(Product product) {
        this.productName = product.getProductName();
        this.image = product.getProductImage();
        this.price = product.getProductPrice();
    }

}

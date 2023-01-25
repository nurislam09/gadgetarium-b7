package com.example.gadgetariumb7.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AllProductResponse {

    private List<ProductCardResponse> allNewProduct;
    private List<ProductCardResponse> allRecProduct;
    private List<ProductCardResponse> allDiscountProduct;

    public AllProductResponse(List<ProductCardResponse> allNewProduct, List<ProductCardResponse> allRecProduct, List<ProductCardResponse> allDiscountProduct) {
        this.allNewProduct = allNewProduct;
        this.allRecProduct = allRecProduct;
        this.allDiscountProduct = allDiscountProduct;
    }
}

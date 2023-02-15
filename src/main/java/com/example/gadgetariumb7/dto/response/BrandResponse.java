package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.entity.Product;
import lombok.*;

//@Builder
@Getter
@Setter
@NoArgsConstructor
public class BrandResponse {
    private String brandName;

    public BrandResponse(Product product) {
        this.brandName = product.getBrand().getBrandName();
    }
}

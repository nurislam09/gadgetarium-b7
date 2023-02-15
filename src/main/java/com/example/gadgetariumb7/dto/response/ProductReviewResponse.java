package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.entity.Brand;
import com.example.gadgetariumb7.db.entity.Product;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewResponse {
    private Long id;
    private String image;
    private String name;

    private BrandResponse brandResponse;
    private Long productVendorCode;

    public ProductReviewResponse(Product product) {
        this.id = product.getId();
        this.image = product.getProductImage();
        this.name = product.getProductName();
        this.productVendorCode = product.getProductVendorCode();
    }

    public ProductReviewResponse(Long id, String image, String name, Long productVendorCode) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.productVendorCode = productVendorCode;
    }
}

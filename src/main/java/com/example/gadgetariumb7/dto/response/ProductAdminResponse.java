package com.example.gadgetariumb7.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class ProductAdminResponse {
    private Long id;
    private String productImage;
    private int productVendorCode;
    private String productName;
    private int productCount;
    private int countSubproducts;
    private LocalDateTime createAt;
    private int productPrice;
    private int currentPrice;
    private int discountPrice;

    public ProductAdminResponse(Long id, int productVendorCode, String productName, int productCount, int countSubproducts, LocalDateTime createAt, int productPrice) {
        this.id = id;
        this.productVendorCode = productVendorCode;
        this.productName = productName;
        this.productCount = productCount;
        this.countSubproducts = countSubproducts;
        this.createAt = createAt;
        this.productPrice = productPrice;
    }
}

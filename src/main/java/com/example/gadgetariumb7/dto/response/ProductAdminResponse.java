package com.example.gadgetariumb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ProductAdminResponse {
    private Long id;
    private String productImages;
    private int productVendorCode;
    private String productName;
    private int productCount;
    private int countSubproducts;
//    private LocalDateTime createAt;
    private int productPrice;
    private int currentPrice;
    private byte discountPrice;

    public ProductAdminResponse(Long id, int productVendorCode, String productName, int productCount, int countSubproducts, int productPrice, int currentPrice, byte discountPrice) {
        this.id = id;
        this.productVendorCode = productVendorCode;
        this.productName = productName;
        this.productCount = productCount;
        this.countSubproducts = countSubproducts;
        this.productPrice = productPrice;
        this.currentPrice = currentPrice;
        this.discountPrice = discountPrice;
    }
}

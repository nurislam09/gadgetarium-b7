package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductAdminResponse {
    private Long id;
    private String image;
    private int productVendorCode;
    private int productCount;
    private LocalDateTime createAt;
    private int productPrice;
    private int currentPrice;
    private Discount discount;
}

package com.example.gadgetariumb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubproductCardResponse {
    private Long id;
    private String image;
    private Map<String, String> characteristics;
    private String color;
    private byte rating;
    private byte amountOfDiscount;
    private int countOfReviews;
    private int countOfSubproduct;
    private Long vendorCode;
    private int orderCount;
    private int price;
}

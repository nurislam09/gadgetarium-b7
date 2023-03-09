package com.example.gadgetariumb7.dto.response;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCompareResponse {
    private Long id;
    private String productName;
    private String image;
    private Map<String, String> characteristics;
    private String brandName;
    private String color;
    private int price;
}


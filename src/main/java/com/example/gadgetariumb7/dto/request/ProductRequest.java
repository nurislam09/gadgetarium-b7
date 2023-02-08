package com.example.gadgetariumb7.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String productName;

    private int productVendorCode;

    private byte guarantee;

    private String videoReview;

    private String PDF;

    private String description;

    private Long brandId;

    private Long categoryId;

    private Long subCategoryId;

    private List<SubProductRequest> subProductRequests;
}

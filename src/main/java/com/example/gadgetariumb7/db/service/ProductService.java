package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.response.ProductCardResponse;

import java.util.List;

public interface ProductService {

    List<ProductCardResponse> getAllNewProduct();

    List<ProductCardResponse> getAllDiscountProduct();

    List<ProductCardResponse> getAllRecommendationProduct();
}

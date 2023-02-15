package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.response.ProductAdminPaginationResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    List<ProductCardResponse> getAllDiscountProductToMP(int page, int size);

    List<ProductCardResponse> getAllNewProductToMP(int page, int size);

    List<ProductCardResponse> getAllRecommendationProductToMP(int page, int size);

    ProductAdminPaginationResponse getProductAdminResponses(String searchText, String productType, String fieldToSort, String discountField, LocalDate startDate, LocalDate endDate, int page, int size);

    SimpleResponse addProduct(ProductRequest productRequest);

    SimpleResponse delete(Long id);

    SimpleResponse update(Long id, Long vendorCode, Integer productCount, Integer productPrice);

    List<ProductCardResponse> filterByParameters(String text, String categoryName, String fieldSort, String discountField, String subCategoryName, Integer minPrice, Integer maxPrice, List<String> colors, Integer memory, Byte ram, int size);

}

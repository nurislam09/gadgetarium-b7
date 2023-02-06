package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    AllProductResponse getAllProductToMP();

    List<ProductAdminResponse> getProductAdminResponses(String searchText, String productType, String fieldToSort, String discountField, LocalDate startDate, LocalDate endDate, int page, int size);

    SimpleResponse addProduct(ProductRequest productRequest, int price);

    SimpleResponse delete(Long id);

    SimpleResponse update(Long id, Integer vendorCode, Integer productCount, Integer productPrice);

    List<ProductCardResponse> filterByParameters(String categoryName, String fieldSort, String discountField,  String subCategoryName, Integer minPrice, Integer maxPrice, List<String> colors, Integer memory, Byte ram);

}

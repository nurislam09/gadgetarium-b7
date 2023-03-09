package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.response.InforgraphicsResponse;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.request.ProductUpdateRequest;
import com.example.gadgetariumb7.dto.response.ProductAdminPaginationResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.ProductSingleResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProductService {

    List<ProductCardResponse> getAllDiscountProductToMP(int page, int size);

    List<ProductCardResponse> getAllNewProductToMP(int page, int size);

    List<ProductCardResponse> getAllRecommendationProductToMP(int page, int size);

    ProductAdminPaginationResponse getProductAdminResponses(String searchText, String productType, String fieldToSort, String discountField, LocalDate startDate, LocalDate endDate, int page, int size);

    SimpleResponse addProduct(ProductRequest productRequest) throws IOException;

    InforgraphicsResponse infographics() throws NullPointerException;

    SimpleResponse delete(Long id);

    SimpleResponse update(ProductUpdateRequest productUpdateRequest);

    List<ProductCardResponse> filterByParameters(String text, String fieldToSort, String discountField, String categoryName, String subCategoryName, Integer minPrice, Integer maxPrice, List<String> colors,
                                                 Integer memory, Byte ram, String laptopCPU, String screenResolution, String screenSize, String screenDiagonal, String batteryCapacity,
                                                 String wirelessInterface, String caseShape, String braceletMaterial, String housingMaterial, String gender, String waterProof, int size) throws NotFoundException;

    ProductSingleResponse getProductById(Long productId, String attribute, Integer size);

    List<ProductCardResponse> getViewedProducts();

    Map<String, String> getColorsFromDB();
}

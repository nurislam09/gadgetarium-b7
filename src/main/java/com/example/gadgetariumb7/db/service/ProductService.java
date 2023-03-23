package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.response.*;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.request.ProductUpdateRequest;
import com.example.gadgetariumb7.exceptions.NotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    List<ProductCardResponse> getAllDiscountProductToMP(int page, int size);

    List<ProductCardResponse> getAllNewProductToMP(int page, int size);

    List<ProductCardResponse> getAllRecommendationProductToMP(int page, int size);

    ProductAdminPaginationResponse getProductAdminResponses(String searchText, String productType, String fieldToSort, String discountField, LocalDate startDate, LocalDate endDate, int page, int size);

    SimpleResponse addProduct(ProductRequest productRequest) throws IOException;

    InforgraphicsResponse infographics() throws NullPointerException;

    SimpleResponse delete(Long id);

    SimpleResponse update(ProductUpdateRequest productUpdateRequest);

    CatalogResponse filterByParameters(String text, String fieldToSort, String discountField, String categoryName, List<String> subCategoryName, Integer minPrice, Integer maxPrice, List<String> colors,
                                       List<Integer> memory, List<Byte> ram, List<String> laptopCPU, List<String> screenResolution, List<String> screenSize, List<String> screenDiagonal, List<String> batteryCapacity,
                                       List<String> wirelessInterface, List<String> caseShape, List<String> braceletMaterial, List<String> housingMaterial, List<String> gender, List<String> waterProof, int size) throws NotFoundException;

    ProductSingleResponse getProductById(Long productId, String attribute, Integer size);

    List<ProductCardResponse> getViewedProducts();

    List<ColorResponse> colorCount(Long categoryId);
}

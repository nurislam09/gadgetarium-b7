package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/")
public class ProductController {

    private final ProductService productService;

    @GetMapping("getDis")
    @Operation(summary = "get product with discount")
    public List<ProductCardResponse> getAllDiscountProduct() {
        return productService.getAllDiscountProduct();
    }

    @GetMapping("getNew")
    @Operation(summary = "get new products", description = "products created at not earlier then week")
    public List<ProductCardResponse> getAllNewProduct() {
        return productService.getAllNewProduct();
    }

    @GetMapping("getRec")
    @Operation(summary = "get recommendation products", description = "0 is 'new' status , 1 is 'recommendation' status")
    public List<ProductCardResponse> getAllRecommendationProduct() {
        return productService.getAllRecommendationProduct();
    }
}

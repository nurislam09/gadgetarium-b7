package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.ProductSingleResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Product API")
public class ProductUserController {

    private final ProductService productService;

    @Operation(summary = "Get all products with discount to main page", description = "This endpoint return ProductResponse with discounts")
    @GetMapping("/discounts")
    public List<ProductCardResponse> getAllDiscountProductMainPage(@RequestParam int page, @RequestParam int size) {
        return productService.getAllDiscountProductToMP(page, size);
    }

    @Operation(summary = "Get all products with new status to main page", description = "This endpoint return ProductResponse with new status")
    @GetMapping("/newProducts")
    public List<ProductCardResponse> getAllNewProductMainPage(@RequestParam int page, @RequestParam int size) {
        return productService.getAllNewProductToMP(page, size);
    }

    @Operation(summary = "Get all products with recommendation status to main page", description = "This endpoint return ProductResponse with recommendation status")
    @GetMapping("/recommendations")
    public List<ProductCardResponse> getAllRecommendationProductMainPage(@RequestParam int page, @RequestParam int size) {
        return productService.getAllRecommendationProductToMP(page, size);
    }

    @Operation(summary = "Get products from catalog", description = "The user can filter by 7 parameters and categoryName is always required in filtering, but others no because user shouldn't give them all." +
            "The field 'fieldToSort' is using if the user wants to sort the products by next fields: Новинки, По акции(if you choose this field you need to write also to discountField one of next three: Все акции, До 50%, Свыше 50%), Рекомендуемые, По увеличению цены, По уменьшению цены.'" +
            "Also if the 'text' is null will work only the filter and sort, but if you write something int text then will work only searching. Required only the size")
    @GetMapping("/catalog")
    @PreAuthorize("isAuthenticated()")
    public List<ProductCardResponse> filterByParameters(@RequestParam(required = false) String text, @RequestParam(required = false) String fieldToSort, @RequestParam(required = false) String discountField, @RequestParam(required = false) String categoryName, @RequestParam(required = false) String subCategoryName, @RequestParam(required = false) Integer minPrice, @RequestParam(required = false) Integer maxPrice, @RequestParam(required = false) List<String> colors,
                                                        @RequestParam(required = false) Integer memory, @RequestParam(required = false) Byte ram, @RequestParam(required = false) String laptopCPU, @RequestParam(required = false) String screenResolution, @RequestParam(required = false) String screenSize, @RequestParam(required = false) String screenDiagonal, @RequestParam(required = false) String batteryCapacity,
                                                        @RequestParam(required = false) String wirelessInterface, @RequestParam(required = false) String caseShape, @RequestParam(required = false) String braceletMaterial, @RequestParam(required = false) String housingMaterial, @RequestParam(required = false) String gender, @RequestParam(required = false) String waterProof, @RequestParam() int size) throws NotFoundException {
        return productService.filterByParameters(text, fieldToSort, discountField, categoryName, subCategoryName, minPrice, maxPrice, colors,
                memory, ram, laptopCPU, screenResolution, screenSize, screenDiagonal, batteryCapacity,
                wirelessInterface, caseShape, braceletMaterial, housingMaterial, gender, waterProof, size);
    }

    @Operation(summary = "Get last viewed products", description = "This method shows last viewed products")
    @GetMapping("/viewed")
    @PreAuthorize("isAuthenticated()")
    public List<ProductCardResponse> getViewedProducts() {
        return productService.getViewedProducts();
    }

    @Operation(summary = "Get product by id", description = "to get the inner page of product you always need to give the id of product and by default the attribute 'Описание'" +
            "size is required if you need attribute 'Отзывы'")
    @GetMapping("/product")
    @PreAuthorize("isAuthenticated()")
    public ProductSingleResponse getProductById(@RequestParam(value = "id") Long productId, @RequestParam String attribute, @RequestParam(required = false) Integer size) throws NotFoundException {
        return productService.getProductById(productId, attribute, size);
    }

    @Operation(summary = "Get colors from database", description = "")
    @GetMapping("/getColorFromDatabase")
    @PreAuthorize("isAuthenticated()")
    public Map<String, String> getColorsFromDB() {
        return productService.getColorsFromDB();
    }
}
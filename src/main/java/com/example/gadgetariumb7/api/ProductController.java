package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.InforgraphicsResponse;
import com.example.gadgetariumb7.dto.request.ProductUpdateRequest;

import com.example.gadgetariumb7.dto.response.ProductAdminPaginationResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.ProductSingleResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.gadgetariumb7.dto.request.ProductRequest;

import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Product api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/discountsProducts")
    @Operation(summary = "Get all products with discount to main page", description = "This endpoint return ProductResponse with discounts")
    public List<ProductCardResponse> getAllDiscountProductMainPage(@RequestParam int page, @RequestParam int size) {
        return productService.getAllDiscountProductToMP(page, size);
    }

    @GetMapping("/newProducts")
    @Operation(summary = "Get all products with new status to main page", description = "This endpoint return ProductResponse with new status")
    public List<ProductCardResponse> getAllNewProductMainPage(@RequestParam int page, @RequestParam int size) {
        return productService.getAllNewProductToMP(page, size);
    }

    @GetMapping("/recommendationsProducts")
    @Operation(summary = "Get all products with recommendation status to main page", description = "This endpoint return ProductResponse with recommendation status")
    public List<ProductCardResponse> getAllRecommendationProductMainPage(@RequestParam int page, @RequestParam int size) {
        return productService.getAllRecommendationProductToMP(page, size);
    }

    @Operation(summary = "Get all products to admin page", description = "This endpoint return all products by product type for ADMIN")
    @GetMapping("/getAllProducts")
    @PreAuthorize("hasAuthority('Admin')")
    public ProductAdminPaginationResponse getAllProduct(
            @RequestParam(value = "productType") String productType,
            @RequestParam(value = "searchText", required = false) String searchText,
            @RequestParam(value = "fieldToSort", required = false) String fieldToSort,
            @RequestParam(value = "discountField", required = false) String discountField,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam int page,
            @RequestParam int size) {
        return productService.getProductAdminResponses(searchText, productType, fieldToSort, discountField, startDate, endDate, page, size);
    }

    @Operation(summary = "delete product", description = "This endpoint delete product by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse delete(@PathVariable Long id) {
        return productService.delete(id);
    }

    @Operation(summary = "update product", description = "This endpoint update product by id")
    @PutMapping()
    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse update(@RequestBody ProductUpdateRequest productUpdateRequest) {
        return productService.update(productUpdateRequest);
    }

    @Operation(summary = "This method for save product",
            description = "The save product with different types and options")
    @PostMapping()
    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse save(@RequestBody ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @Operation(summary = "get products from catalog", description = "the user can filter by 7 parameters and categoryName is always required in filtering, but others no because user shouldn't give them all." +
            "The field 'fieldToSort' is using if the user wants to sort the products by next fields: Новинки, По акции(if you choose this field you need to write also to discountField one of next three: Все акции, До 50%, Свыше 50%), Рекомендуемые, По увеличению цены, По уменьшению цены.'" +
            "Also if the 'text' is null will work only the filter and sort, but if you write something int text then will work only searching. Required only the size")
    @GetMapping("/catalog")
    @PreAuthorize("isAuthenticated()")
    public List<ProductCardResponse> filterByParameters(@RequestParam(value = "text", required = false) String text,
                                                        @RequestParam(value = "categoryName", required = false) String categoryName,
                                                        @RequestParam(value = "fieldToSort", required = false) String fieldToSort,
                                                        @RequestParam(value = "discountField", required = false) String discountField,
                                                        @RequestParam(value = "subCategoryName", required = false) String subCategoryName,
                                                        @RequestParam(value = "min", required = false) Integer minPrice,
                                                        @RequestParam(value = "max", required = false) Integer maxPrice,
                                                        @RequestParam(value = "colors", required = false) List<String> colors,
                                                        @RequestParam(value = "memory", required = false) Integer memory,
                                                        @RequestParam(value = "ram", required = false) Byte ram,
                                                        @RequestParam int size) throws NotFoundException {
        return productService.filterByParameters(text, categoryName, fieldToSort, discountField, subCategoryName, minPrice, maxPrice, colors, memory, ram, size);
    }

    @Operation(summary = "Get product by id", description = "to get the inner page of product you always need to give the id of product and by default the attribute 'Описание'" +
            "size is required if you need attribute 'Отзывы'")
    @GetMapping("/product")
    @PreAuthorize("isAuthenticated()")
    public ProductSingleResponse getProductById(@RequestParam(value = "id") Long productId, @RequestParam String attribute, @RequestParam(required = false) Integer size) throws NotFoundException{
        return productService.getProductById(productId, attribute, size);
    }


    @Operation(summary = "This method for get information", description = "Get information with product status")
    @GetMapping("/inf")
    @PreAuthorize("hasAuthority('Admin')")
    public InforgraphicsResponse inforgraphic() throws NotFoundException{
        return productService.inforgraphics();
    }
}

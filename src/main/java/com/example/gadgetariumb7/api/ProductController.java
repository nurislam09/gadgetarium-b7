package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gadgetariumb7.dto.request.ProductRequest;

import com.example.gadgetariumb7.dto.response.SimpleResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Product api")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    @Operation(summary = "Get all products to main page", description = "This endpoint return AllProductResponse which contains three different response array")
    public AllProductResponse getAllProductMainPage() {
        return productService.getAllProductToMP();
    }

    @Operation(summary = "all products", description = "This endpoint return all products by product type for ADMIN")
    @GetMapping("/getAllProducts")
    @PreAuthorize("hasAuthority('Admin')")
    private List<ProductAdminResponse> getAllProduct(
            @RequestParam(value = "productType") String productType,
            @RequestParam(value = "fieldToSort", required = false) String fieldToSort,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam int page,
            @RequestParam int size) {
        return productService.getProductAdminResponses(productType, fieldToSort, startDate, endDate, page, size);
    }

    @Operation(summary = "delete product", description = "This endpoint delete product by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    private SimpleResponse delete(@PathVariable Long id) {
        return productService.delete(id);
    }

    @Operation(summary = "update product", description = "This endpoint update product by id")
    @PutMapping()
    @PreAuthorize("hasAuthority('Admin')")
    private SimpleResponse update(
            @RequestParam(value = "ID") Long id,
            @RequestParam(value = "Артикул", required = false) Integer vendorCode,
            @RequestParam(value = "Наименования товара", required = false) Integer productCount,
            @RequestParam(value = "Цена товара", required = false) Integer productPrice) {
        return productService.update(id, vendorCode, productCount, productPrice);
    }

    @Operation(summary = "search product", description = "This endpoint ADMIN can search product by vendor code or product name")
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('Admin')")
    private List<ProductAdminResponse> search(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam int page,
            @RequestParam int size) {
        return productService.search(text, page, size);
    }

    @Operation(summary = "This method for save product",
            description = "The save product with different types and options")
    @PostMapping()
    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse save(@RequestBody ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

}

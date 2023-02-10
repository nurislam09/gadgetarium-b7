package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.request.InforgraphicsRequest;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
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

    @GetMapping()
    @Operation(summary = "Get all products to main page", description = "This endpoint return AllProductResponse which contains three different response array")
    public AllProductResponse getAllProductMainPage() {
        return productService.getAllProductToMP();
    }

    @Operation(summary = "Get all products to admin page", description = "This endpoint return all products by product type for ADMIN")
    @GetMapping("/getAllProducts")
    @PreAuthorize("hasAuthority('Admin')")
    public List<ProductAdminResponse> getAllProduct(
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
    public SimpleResponse update(
            @RequestParam(value = "ID") Long id,
            @RequestParam(value = "Артикул", required = false) Integer vendorCode,
            @RequestParam(value = "Наименования товара", required = false) Integer productCount,
            @RequestParam(value = "Цена товара", required = false) Integer productPrice) {
        return productService.update(id, vendorCode, productCount, productPrice);
    }

    @Operation(summary = "This method for save product",
            description = "The save product with different types and options")
    @PostMapping()
    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse save(@RequestBody ProductRequest productRequest, @RequestParam(value = "price") int price) {
        return productService.addProduct(productRequest, price);
    }

    @Operation(summary = "this method for get information",
            description = "get information with product status")
    @GetMapping("/inf")
   // @PreAuthorize("hasAuthority('Admin')")
    public InforgraphicsRequest inforgraphic() {
        return productService.inforgraphics();
    }

}

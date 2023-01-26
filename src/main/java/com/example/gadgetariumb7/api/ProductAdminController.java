package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "product API")
public class ProductAdminController {
    private final ProductService productService;

    @Operation(summary = "all products", description = "this endpoint return all products by product type for ADMIN")
    @GetMapping("/getAllProducts")
//    @PreAuthorize("hasAuthority('Admin')")
    private List<ProductAdminResponse> getAllProduct(
            @RequestParam(value = "productType") String productType,
            @RequestParam(value = "fieldToSort", required = false) String fieldToSort,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam int page,
            @RequestParam int size) {
        return productService.getProductAdminResponses(productType, fieldToSort, startDate, endDate, page, size);
    }

    @Operation(summary = "delete product", description = "this endpoint delete product by id")
    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('Admin')")
    private SimpleResponse delete(@PathVariable Long id) {
        return productService.delete(id);
    }

    @Operation(summary = "update product", description = "this endpoint update product by id")
    @PutMapping("/update")
//    @PreAuthorize("hasAuthority('Admin')")
    private SimpleResponse update(
            @RequestParam(value = "ID") Long id,
            @RequestParam(value = "Артикул", required = false) Integer vendorCode,
            @RequestParam(value = "Наименования товара", required = false) Integer productCount,
            @RequestParam(value = "Цена товара", required = false) Integer productPrice) {
        return productService.update(id, vendorCode, productCount, productPrice);
    }

    @Operation(summary = "search product", description = "in this endpoint ADMIN can search product by vendor code or product name")
    @GetMapping("/search")
//    @PreAuthorize("hasAuthority('Admin')")
    private List<ProductAdminResponse> search(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam int page,
            @RequestParam int size) {
        return productService.search(text, page, size);
    }
}

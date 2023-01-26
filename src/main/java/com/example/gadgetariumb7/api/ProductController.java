package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
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
import java.io.IOException;

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

    @Operation(summary = "This method for save product",
    description = "The save product with different types and options")
    @PostMapping("/saveProduct")
    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse save (@RequestBody ProductRequest productRequest) throws IOException {
       return productService.addProduct(productRequest);
    }

}

package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.impl.ProductServiceImpl;
import com.example.gadgetariumb7.dto.request.ProductRequest;

import com.example.gadgetariumb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductAdminController {

    private final ProductServiceImpl productService;

    @Operation(summary = "This method for save product",
    description = "The save product with different types and options")
    @PostMapping("/saveProduct")
//    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse save (@RequestBody ProductRequest productRequest) throws IOException {
       return productService.addProduct(productRequest);
    }
}

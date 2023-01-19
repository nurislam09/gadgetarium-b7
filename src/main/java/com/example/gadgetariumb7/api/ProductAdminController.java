package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.service.impl.ProductServiceImpl;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductAdminController {

    private final ProductServiceImpl productService;

    @Operation(summary = "This method for save product",
    description = "The save product with different types and options")
//    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/saveProduct")
    public ResponseEntity<String> save (@RequestBody ProductRequest productRequest) throws IOException {
        productService.addProduct(productRequest);
        return new ResponseEntity<>("success", HttpStatus.ACCEPTED);


    }
}

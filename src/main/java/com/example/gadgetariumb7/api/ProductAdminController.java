package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductServiceImpl;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductAdminController {
    private final ProductServiceImpl productService;

    @GetMapping("/getAllProducts")
    private List<ProductAdminResponse> getAllProduct(){
        return productService.productAdminResponses();
    }
}

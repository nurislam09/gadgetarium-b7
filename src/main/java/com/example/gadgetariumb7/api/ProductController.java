package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public List<ProductCardResponse> getAllDiscountProduct(){
        return productService.getAllDiscountProduct();
    }
}

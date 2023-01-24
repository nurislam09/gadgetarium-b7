package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/product/")
public class ProductController {

    private final ProductService productService;
    @GetMapping("getAllToMP")
    public AllProductResponse getAllProductMainPage(){
        return productService.getAllProductToMP();
    }
}

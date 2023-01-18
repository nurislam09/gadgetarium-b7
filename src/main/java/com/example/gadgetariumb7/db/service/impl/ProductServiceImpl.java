package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;



    @Override
    public SimpleResponse addProduct(ProductRequest productRequest) throws IOException {
        Product product;
        if (productRequest.getCategory().getCategoryName().equals("Phone")) {
            product = new Product(productRequest,productRequest.getProductName());
        }
        else if (productRequest.getCategory().getCategoryName().equals("SmartWatch")) {
            product = new Product(productRequest, productRequest.getGender());
        }
        else if (productRequest.getCategory().getCategoryName().equals("Tablet")) {
            product = new Product(productRequest, productRequest.getScreenDiagonal());
        }
        else if (productRequest.getCategory().getCategoryName().equals("Laptop")){
            product = new Product(productRequest, productRequest.getVideoCardMemory());

        }
        return new SimpleResponse("Successfully saved", "SAVE");
    }

}

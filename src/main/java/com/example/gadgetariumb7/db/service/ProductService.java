package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private  ProductRepository productRepository;

    public List<ProductCardResponse> getAllDiscountProduct(){
        List<ProductCardResponse> discountProducts =  productRepository.getAllDiscountProduct();
        for(ProductCardResponse p : discountProducts){
            p.setProductImage(productRepository.getById(p.getProductId()).getProductImages().get(0));
        }
        return discountProducts;
    }
}

package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Discount;
import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {
    private final ProductRepository productRepository;

    public List<ProductCardResponse> getAllDiscountProduct() {
        List<ProductCardResponse> discountProducts = productRepository.getAllDiscountProduct();
        for (ProductCardResponse p : discountProducts) {
            p.setProductImage(productRepository.getFirstImage(p.getProductId()));
//            if (product.getDiscount() != null) {
//                Discount discount = product.getDiscount();
//                p.setDiscountPrice((p.getProductPrice() * discount.getAmountOfDiscount()) / 100);
//            }
            p.setProductPrice(productRepository.getDiscountPrice(p.getProductId()));
        }
        return discountProducts;
    }

    public List<ProductCardResponse> getAllNewProduct() {
        List<ProductCardResponse> newProduct = productRepository.getAllNewProduct();
        for (ProductCardResponse p : newProduct) {
            p.setProductImage(productRepository.getFirstImage(p.getProductId()));
        }
        return newProduct.stream().limit(5).toList();
    }

    public List<ProductCardResponse> getAllRecommendationProduct() {
        List<ProductCardResponse> recommendations = productRepository.getAllRecommendationProduct();
        for (ProductCardResponse p : recommendations) {
            p.setProductImage(productRepository.getFirstImage(p.getProductId()));
        }
        return recommendations;
    }


    public int getDiscountPrice(Long productId , ProductCardResponse productCardResponse){
        Product product = productRepository.findById(productId).get();
        int amountOfDiscount = 0;
        if (product.getDiscount() != null) {
            Discount discount = product.getDiscount();
            amountOfDiscount= ((productCardResponse.getProductPrice() * discount.getAmountOfDiscount()) / 100);
        }
       return amountOfDiscount;
    }
}

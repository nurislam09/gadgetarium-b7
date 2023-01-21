package com.example.gadgetariumb7.db.service.serviceImpl;

import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public List<ProductCardResponse> getAllDiscountProduct() {
        List<ProductCardResponse> discountProducts = productRepository.getAllDiscountProduct();
        for (ProductCardResponse p : discountProducts) {
            p.setProductImage(productRepository.getFirstImage(p.getProductId()));
            p.setDiscountPrice(productRepository.getDiscountPrice(p.getProductId()));
        }
        return discountProducts;
    }

    public List<ProductCardResponse> getAllNewProduct() {
        List<ProductCardResponse> newProduct = productRepository.getAllNewProduct();
        for (ProductCardResponse p : newProduct) {
            p.setProductImage(productRepository.getFirstImage(p.getProductId()));
            try {
                if (productRepository.getDiscountPrice(p.getProductId()) == 0) {
                    p.setDiscountPrice(p.getProductPrice());
                } else {
                    p.setDiscountPrice(productRepository.getDiscountPrice(p.getProductId()));
                }
            } catch (RuntimeException e) {
                System.out.println("null discount");
            }
        }
        return newProduct.stream().limit(5).toList();
    }

    public List<ProductCardResponse> getAllRecommendationProduct() {
        List<ProductCardResponse> recommendations = productRepository.getAllRecommendationProduct();
        for (ProductCardResponse p : recommendations) {
            p.setProductImage(productRepository.getFirstImage(p.getProductId()));
            try {
                if (productRepository.getDiscountPrice(p.getProductId()) == 0) {
                    p.setDiscountPrice(p.getProductPrice());
                } else {
                    p.setDiscountPrice(productRepository.getDiscountPrice(p.getProductId()));
                }
            } catch (RuntimeException e) {
                System.out.println("null discount");
            }
        }
        return recommendations.stream().limit(5).toList();
    }

}

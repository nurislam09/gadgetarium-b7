package com.example.gadgetariumb7.db.service.serviceImpl;

import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public AllProductResponse getAllProductToMP() {
        List<ProductCardResponse> discountProducts = productRepository.getAllDiscountProduct();
        List<ProductCardResponse> newProducts = productRepository.getAllNewProduct();
        List<ProductCardResponse> recommendations = productRepository.getAllRecommendationProduct();

        recommendations.forEach(r -> {r.setProductImage(productRepository.getFirstImage(r.getProductId())); setDiscountToResponse(r);});
        discountProducts.forEach(r -> {r.setProductImage(productRepository.getFirstImage(r.getProductId())); r.setDiscountPrice(productRepository.getDiscountPrice(r.getProductId()));});
        newProducts.forEach(r -> {r.setProductImage(productRepository.getFirstImage(r.getProductId())); setDiscountToResponse(r);});

        return new AllProductResponse(newProducts, recommendations, discountProducts);
    }

    private void setDiscountToResponse(ProductCardResponse productCardResponse) {
        try {
            if (productRepository.getDiscountPrice(productCardResponse.getProductId()) == 0) {
                productCardResponse.setDiscountPrice(productCardResponse.getProductPrice());
            } else {
                productCardResponse.setDiscountPrice(productRepository.getDiscountPrice(productCardResponse.getProductId()));
            }
        } catch (RuntimeException e) {
            System.out.println("null discount");
        }
    }
}

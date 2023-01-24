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
        List<ProductCardResponse> newProduct = productRepository.getAllNewProduct();
        List<ProductCardResponse> recommendations = productRepository.getAllRecommendationProduct();
        for (ProductCardResponse r : recommendations) {
            for (ProductCardResponse n : newProduct) {
                for (ProductCardResponse d : discountProducts) {
                    r.setProductImage(productRepository.getFirstImage(r.getProductId()));
                    n.setProductImage(productRepository.getFirstImage(n.getProductId()));
                    d.setProductImage(productRepository.getFirstImage(d.getProductId()));

                    d.setProductImage(productRepository.getFirstImage(d.getProductId()));
                    d.setDiscountPrice(productRepository.getDiscountPrice(d.getProductId()));
                    try {
                        if (productRepository.getDiscountPrice(r.getProductId()) == 0 && productRepository.getDiscountPrice(n.getProductId()) == 0) {
                            r.setDiscountPrice(r.getProductPrice());
                            n.setDiscountPrice(n.getProductPrice());
                        } else {
                            r.setDiscountPrice(productRepository.getDiscountPrice(r.getProductId()));
                            n.setDiscountPrice(productRepository.getDiscountPrice(n.getProductId()));
                        }
                    } catch (RuntimeException e) {
                        System.out.println("null discount");
                    }
                }
            }
        }
        return new AllProductResponse(newProduct, recommendations, discountProducts);
    }
}

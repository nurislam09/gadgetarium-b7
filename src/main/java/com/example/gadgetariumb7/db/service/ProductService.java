package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public AllProductResponse getAllProductToMP() {
        List<ProductCardResponse> discountProducts = productRepository.getAllDiscountProduct();
        List<ProductCardResponse> newProducts = productRepository.getAllNewProduct();
        List<ProductCardResponse> recommendations = productRepository.getAllRecommendationProduct();

        recommendations.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getProductId()));
            setDiscountToResponse(r);
        });
        discountProducts.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getProductId()));
            r.setDiscountPrice(productRepository.getDiscountPrice(r.getProductId()));
        });
        newProducts.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getProductId()));
            setDiscountToResponse(r);
        });

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

    public List<ProductCardResponse> filterByParameters(String categoryName, String subCategoryName, Integer minPrice, Integer maxPrice, List<String> colors, Integer memory, Byte ram) {
        List<ProductCardResponse> productCardResponses = productRepository.filterByParameters(categoryName.toUpperCase(), subCategoryName.toUpperCase(), minPrice, maxPrice, colors.stream().map(String::toUpperCase).toList(), memory, ram);
        for (ProductCardResponse productCardResponse : productCardResponses) {
            Optional<Product> productOptional = productRepository.findById(productCardResponse.getProductId());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                if (product.getCategory().getCategoryName().toUpperCase().equalsIgnoreCase(categoryName)) {
                    if (productCardResponse.getDiscountPrice() != 0) {
                        productCardResponse.setDiscountPrice(productRepository.getDiscountPrice(productCardResponse.getProductId()));
                    }
                    int countFeedback = product.getUsersReviews().size();
                    productCardResponse.setCountFeedback(countFeedback);
                    productCardResponse.setProductImage(productRepository.getFirstImage(productCardResponse.getProductId()));
                    setDiscountToResponse(productCardResponse);
                }
            } else {
                return null;
            }
        }
        return productCardResponses;
    }

}

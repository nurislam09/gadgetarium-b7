package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.*;
import com.example.gadgetariumb7.db.enums.Gender;
import com.example.gadgetariumb7.db.repository.*;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.request.SubProductRequest;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SubProductRepository subProductRepository;

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

    public SimpleResponse addProduct(ProductRequest productRequest) {
        Brand brand = brandRepository.findById(productRequest.getBrandId()).get();
        Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
        Subcategory subcategory = subcategoryRepository.findById(productRequest.getCategoryId()).get();

        List<Subproduct> subproducts = new ArrayList<>();
        for (SubProductRequest s : productRequest.getSubProductRequests()) {
            if (category.getCategoryName().equals("Ноутбуки и планшеты") &&
                    productRequest.getLaptopCPU() != null) {
                Subproduct subproduct = new Subproduct(s.getLaptopCPU(), s.getColor(), s.getImages());
                subproducts.add(subproduct);
            } else {
                Subproduct subproduct = new Subproduct(s.getMemory(), s.getColor(), s.getImages());
                subproducts.add(subproduct);
            }
        }

        if (category.getCategoryName().equals("Смартфоны")) {
            Product product = new Product(productRequest, brand, category, subcategory, "Смартфоны");
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            productRepository.save(product);
            subProductRepository.saveAll(subproducts);

        } else if (category.getCategoryName().equals("Смарт-часы и браслеты")) {
            Product product = new Product(productRequest, brand, category, subcategory, Gender.MALE);
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            productRepository.save(product);
            subProductRepository.saveAll(subproducts);

        } else if (productRequest.getMemoryOfTablet() != 0) {
            Product product = new Product(productRequest, brand, category, subcategory, 1, 0.0);
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            productRepository.save(product);
            subProductRepository.saveAll(subproducts);

        } else {
            Product product = new Product(productRequest, brand, category, subcategory, (byte) 1);
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            productRepository.save(product);
        }
        return new SimpleResponse("Product successfully saved", "ok");
    }

}

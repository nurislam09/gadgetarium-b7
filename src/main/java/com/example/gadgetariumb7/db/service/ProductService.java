package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.*;
import com.example.gadgetariumb7.db.repository.*;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ProductAdminResponse> getProductAdminResponses(String productType, String fieldToSort, LocalDate startDate, LocalDate endDate, int page, int size) {
        List<ProductAdminResponse> productAdminResponses = sortingProduct(fieldToSort, productRepository.getAllProductsAdmin(PageRequest.of(page - 1, size)), startDate, endDate);

        switch (productType) {
            case "Все товары" -> {
                return productAdminResponses;
            }
            case "В продаже" -> {
                return productAdminResponses.stream().filter(x -> x.getProductCount() > 0).toList();
            }
            case "В корзине" -> {
                List<Product> productList = new ArrayList<>();
                List<ProductAdminResponse> responseList = new ArrayList<>();

                userRepository.findAll().stream().filter(u -> u.getBasketList() != null).forEach(x -> x.getBasketList().stream().filter(p -> !productList.contains(p)).forEach(productList::add));
                productList.forEach(p -> responseList.add(new ProductAdminResponse(p.getId(), p.getProductVendorCode(), p.getProductName(), p.getProductCount(), p.getSubproducts().size(), p.getCreateAt(), p.getProductPrice())));
                return sortingProduct(fieldToSort, responseList, startDate, endDate);
            }
            case "В избранном" -> {
                List<Product> productList = new ArrayList<>();
                List<ProductAdminResponse> responseList = new ArrayList<>();
                userRepository.findAll().stream().filter(u -> u.getFavoritesList() != null).forEach(x -> x.getFavoritesList().stream().filter(p -> !productList.contains(p)).forEach(productList::add));
                productList.forEach(p -> responseList.add(new ProductAdminResponse(p.getId(), p.getProductVendorCode(), p.getProductName(), p.getProductCount(), p.getSubproducts().size(), p.getCreateAt(), p.getProductPrice())));
                return sortingProduct(fieldToSort, responseList, startDate, endDate);
            }
        }

        return productAdminResponses;
    }

    public SimpleResponse delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product for delete not found!"));
        product.setDiscount(null);
        productRepository.delete(product);
        return new SimpleResponse("Product successfully deleted!", "ok");
    }

    public SimpleResponse update(Long id, Integer vendorCode, Integer productCount, Integer productPrice) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product for delete not found!"));
        if (vendorCode != null) product.setProductVendorCode(vendorCode);
        if (productCount != null) product.setProductCount(productCount);
        if (productPrice != null) product.setProductPrice(productPrice);
        productRepository.save(product);
        return new SimpleResponse("Product successfully updated", "ok");
    }

    public List<ProductAdminResponse> search(String text, int page, int size) {
        return sortingProduct(null, productRepository.search(text.toUpperCase(), PageRequest.of(page - 1, size)), null, null);
    }

    private List<ProductAdminResponse> sortingProduct(String fieldToSort, List<ProductAdminResponse> products, LocalDate startDate, LocalDate endDate) {
        if (fieldToSort != null) {
            switch (fieldToSort) {
                case "Наименование товара" ->
                        products.sort(Comparator.comparingInt(ProductAdminResponse::getProductCount).reversed());
                case "Дата создания" ->
                        products.sort(Comparator.comparing(ProductAdminResponse::getCreateAt).reversed());
                case "Кол-во" ->
                        products.sort(Comparator.comparing(ProductAdminResponse::getCountSubproducts).reversed());
                case "Цена товара" ->
                        products.sort(Comparator.comparing(ProductAdminResponse::getProductPrice).reversed());
                case "Текущая цена" ->
                        products.sort(Comparator.comparing(ProductAdminResponse::getCurrentPrice).reversed());
            }
        }

        products.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getId()));
            setDiscountToResponse(null, r);
        });

        if (startDate != null && endDate != null)
            return products.stream().filter(p -> p.getCreateAt().toLocalDate().isAfter(startDate) && p.getCreateAt().toLocalDate().isBefore(endDate)).toList();

        return products;
    }

    public AllProductResponse getAllProductToMP() {
        List<ProductCardResponse> discountProducts = productRepository.getAllDiscountProduct();
        List<ProductCardResponse> newProducts = productRepository.getAllNewProduct();
        List<ProductCardResponse> recommendations = productRepository.getAllRecommendationProduct();

        recommendations.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getProductId()));
            setDiscountToResponse(r, null);
        });
        discountProducts.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getProductId()));
            r.setDiscountPrice(productRepository.getDiscountPrice(r.getProductId()));
        });
        newProducts.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getProductId()));
            setDiscountToResponse(r, null);
        });

        return new AllProductResponse(newProducts, recommendations, discountProducts);
    }

    private void setDiscountToResponse(ProductCardResponse productCardResponse, ProductAdminResponse productAdminResponse) {
        try {
            if (productCardResponse != null) {
                if (productRepository.getDiscountPrice(productCardResponse.getProductId()) == 0) {
                    productCardResponse.setDiscountPrice(productCardResponse.getProductPrice());
                } else {
                    productCardResponse.setDiscountPrice(productRepository.getDiscountPrice(productCardResponse.getProductId()));
                }
            } else if (productAdminResponse != null){
                if (productRepository.getDiscountPrice(productAdminResponse.getId()) == 0) {
                    productAdminResponse.setDiscountPrice(productAdminResponse.getDiscountPrice());
                } else {
                    productAdminResponse.setDiscountPrice((productAdminResponse.getProductPrice() - productRepository.getDiscountPrice(productAdminResponse.getId())) * 100 /  productAdminResponse.getProductPrice());
                    productAdminResponse.setCurrentPrice(productRepository.getDiscountPrice(productAdminResponse.getId()));
                }
            }
        } catch (RuntimeException e) {
            System.out.println("null discount");
        }
    }
}

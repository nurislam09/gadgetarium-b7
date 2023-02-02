package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.*;
import com.example.gadgetariumb7.db.enums.Gender;
import com.example.gadgetariumb7.db.enums.ProductStatus;
import com.example.gadgetariumb7.db.repository.*;
import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.request.SubProductRequest;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.*;

import com.example.gadgetariumb7.db.repository.ProductRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public List<ProductAdminResponse> getProductAdminResponses(String searchText, String productType, String fieldToSort, String discountField, LocalDate startDate, LocalDate endDate, int page, int size) {
        List<ProductAdminResponse> productAdminResponses;
        if (searchText == null)
            productAdminResponses = sortingProduct(fieldToSort, discountField, productRepository.getAllProductsAdmin(PageRequest.of(page - 1, size)), startDate, endDate);
        else
            productAdminResponses = sortingProduct(fieldToSort, discountField, productRepository.search(searchText, PageRequest.of(page - 1, size)), startDate, endDate);

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

                userRepository.findAll().stream().filter(u -> u.getBasketList() != null).forEach(x -> x.getBasketList().keySet().stream().filter(p -> !productList.contains(p)).forEach(productList::add));
                productList.forEach(p -> responseList.add(new ProductAdminResponse(p.getId(), p.getProductVendorCode(), p.getProductName(), p.getProductCount(), p.getSubproducts().size(), p.getCreateAt(), p.getProductPrice(), p.getProductStatus())));
                return sortingProduct(fieldToSort, discountField, responseList, startDate, endDate);
            }
            case "В избранном" -> {
                List<Product> productList = new ArrayList<>();
                List<ProductAdminResponse> responseList = new ArrayList<>();

                userRepository.findAll().stream().filter(u -> u.getFavoritesList() != null).forEach(x -> x.getFavoritesList().stream().filter(p -> !productList.contains(p)).forEach(productList::add));
                productList.forEach(p -> responseList.add(new ProductAdminResponse(p.getId(), p.getProductVendorCode(), p.getProductName(), p.getProductCount(), p.getSubproducts().size(), p.getCreateAt(), p.getProductPrice(), p.getProductStatus())));

                return sortingProduct(fieldToSort, discountField, responseList, startDate, endDate);
            }
        }
        return productAdminResponses;
    }

    @Override
    public SimpleResponse delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product for delete not found!"));
        product.setDiscount(null);
        productRepository.delete(product);
        return new SimpleResponse("Product successfully deleted!", "ok");
    }

    @Override
    public SimpleResponse update(Long id, Integer vendorCode, Integer productCount, Integer productPrice) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product for update not found!"));
        if (vendorCode != null) product.setProductVendorCode(vendorCode);
        if (productCount != null) product.setProductCount(productCount);
        if (productPrice != null) product.setProductPrice(productPrice);
        productRepository.save(product);
        return new SimpleResponse("Product successfully updated", "ok");
    }

    private List<ProductAdminResponse> sortingProduct(String fieldToSort, String discountField, List<ProductAdminResponse> products, LocalDate startDate, LocalDate endDate) {
        products.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getId()));
            setDiscountToResponse(null, r);
        });

        if (fieldToSort != null) {
            switch (fieldToSort) {
                case "Новинки" ->
                        products = products.stream().filter(x -> x.getProductStatus() == ProductStatus.NEW).toList();
                case "По акции" -> {
                    if (discountField != null) {
                        switch (discountField) {
                            case "Все акции" ->
                                    products = products.stream().filter(x -> x.getAmountOfDiscount() > 0).toList();
                            case "До 50%" ->
                                    products = products.stream().filter(x -> x.getAmountOfDiscount() < 50 && x.getAmountOfDiscount() > 0).toList();
                            case "Свыше 50%" ->
                                    products = products.stream().filter(x -> x.getAmountOfDiscount() > 50).toList();
                        }
                    }
                }
                case "Рекомендуемые" ->
                        products = products.stream().filter(x -> x.getProductStatus() == ProductStatus.RECOMMENDATION).toList();
                case "По увеличению цены" -> products.sort(Comparator.comparing(ProductAdminResponse::getProductPrice));
                case "По уменьшению цены" ->
                        products.sort(Comparator.comparing(ProductAdminResponse::getProductPrice).reversed());
            }
        }
        if (startDate != null && endDate != null)
            return products.stream().filter(p -> p.getCreateAt().toLocalDate().isAfter(startDate) && p.getCreateAt().toLocalDate().isBefore(endDate)).toList();

        return products;
    }

    @Override
    public AllProductResponse getAllProductToMP() {
        List<ProductCardResponse> discountProducts = productRepository.getAllDiscountProduct();
        List<ProductCardResponse> newProducts = productRepository.getAllNewProduct();
        List<ProductCardResponse> recommendations = productRepository.getAllRecommendationProduct();

        recommendations.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getProductId()));
            setDiscountToResponse(r, null);
            r.setCountOfReview(productRepository.getAmountOfFeedback(r.getProductId()));
        });
        discountProducts.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getProductId()));
            r.setDiscountPrice(productRepository.getDiscountPrice(r.getProductId()));
            r.setCountOfReview(productRepository.getAmountOfFeedback(r.getProductId()));
        });
        newProducts.forEach(r -> {
            r.setProductImage(productRepository.getFirstImage(r.getProductId()));
            setDiscountToResponse(r, null);
            r.setCountOfReview(productRepository.getAmountOfFeedback(r.getProductId()));
        });

        return new AllProductResponse(newProducts, recommendations, discountProducts);
    }

    private void setDiscountToResponse(ProductCardResponse productCardResponse, ProductAdminResponse productAdminResponse) {
        try {
            if (productCardResponse != null) {
                if (productRepository.getDiscountPrice(productCardResponse.getProductId()) == 0) {
                    productCardResponse.setDiscountPrice(productCardResponse.getProductPrice());
                } else {
                    productCardResponse.setDiscountPrice((int) Math.floor(productRepository.getDiscountPrice(productCardResponse.getProductId())));
                }
            } else if (productAdminResponse != null) {
                if (productRepository.getDiscountPrice(productAdminResponse.getId()) == 0) {
                    productAdminResponse.setAmountOfDiscount(productAdminResponse.getAmountOfDiscount());
                } else {
                    productAdminResponse.setAmountOfDiscount(productRepository.getAmountOfDiscount(productAdminResponse.getId()));
                    productAdminResponse.setCurrentPrice(productRepository.getDiscountPrice(productAdminResponse.getId()));
                }
            }
        } catch (RuntimeException e) {
            System.out.println("null discount");
        }
    }

    @Override
    public SimpleResponse addProduct(ProductRequest productRequest, int price) {
        Brand brand = brandRepository.findById(productRequest.getBrandId()).orElseThrow(() -> new NotFoundException("Brand not found"));
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new NotFoundException("Category not found"));
        Subcategory subcategory = subcategoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new NotFoundException("Subcategory not found"));

        List<Subproduct> subproducts = new ArrayList<>();
        for (SubProductRequest s : productRequest.getSubProductRequests()) {
            if (category.getCategoryName().equals("Ноутбуки и планшеты") &&
                    productRequest.getLaptopCPU() != null) {
                Subproduct subproduct = new Subproduct(s.getLaptopCPU(), s.getColor(), s.getImages(), price);
                subproducts.add(subproduct);
            } else {
                Subproduct subproduct = new Subproduct(s.getMemory(), s.getColor(), s.getImages(), price);
                subproducts.add(subproduct);
            }
        }

        Product product;
        if (category.getCategoryName().equals("Смартфоны")) {
            product = new Product(productRequest, price, brand, category, subcategory, "Смартфоны");
        } else if (category.getCategoryName().equals("Смарт-часы и браслеты")) {
            product = new Product(productRequest, price, brand, category, subcategory, Gender.MALE);
        } else if (productRequest.getMemoryOfTablet() != 0) {
            product = new Product(productRequest, price, brand, category, subcategory, 1, 0.0);
        } else {
            product = new Product(productRequest, price, brand, category, subcategory, (byte) 1);
        }
        product.setCreateAt(LocalDateTime.now());
        product.setSubproducts(subproducts);
        subproducts.forEach(s -> s.setProduct(product));
        productRepository.save(product);

        return new SimpleResponse("Product successfully saved", "ok");
    }
}

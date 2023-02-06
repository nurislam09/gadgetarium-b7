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
import java.util.stream.Collectors;

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
                    productCardResponse.setDiscountPrice(Math.round(productRepository.getDiscountPrice(productCardResponse.getProductId())));
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
    public SimpleResponse addProduct(ProductRequest productRequest) {
        Brand brand = brandRepository.findById(productRequest.getBrandId()).orElseThrow(() -> new NotFoundException("Brand not found"));
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new NotFoundException("Category not found"));
        Subcategory subcategory = subcategoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new NotFoundException("Subcategory not found"));

        List<Subproduct> subproducts = new ArrayList<>();
        for (SubProductRequest s : productRequest.getSubProductRequests()) {
            Subproduct subproduct;
            if (category.getCategoryName().equals("Ноутбуки и планшеты") &&
                    productRequest.getLaptopCPU() != null) {
                subproduct = new Subproduct(s.getLaptopCPU(), s.getColor(), s.getImages());
            } else {
                subproduct = new Subproduct(s.getMemory(), s.getColor(), s.getImages());
            }
            subproducts.add(subproduct);
        }

        if (category.getCategoryName().equals("Смартфоны")) {
            Product product = new Product(productRequest, brand, category, subcategory, "Смартфоны");
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            productRepository.save(product);
        } else if (category.getCategoryName().equals("Смарт-часы и браслеты")) {
            Product product = new Product(productRequest, brand, category, subcategory, Gender.MALE);
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            productRepository.save(product);
        } else if (productRequest.getMemoryOfTablet() != 0) {
            Product product = new Product(productRequest, brand, category, subcategory, 1, 0.0);
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            productRepository.save(product);
        } else {
            Product product = new Product(productRequest, brand, category, subcategory, (byte) 1);
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            productRepository.save(product);
        }

        return new SimpleResponse("Product successfully saved", "ok");
    }

    @Override
    public List<ProductCardResponse> filterByParameters(String categoryName, String fieldToSort, String discountField, String subCategoryName, Integer minPrice, Integer maxPrice, List<String> colors, Integer memory, Byte ram) throws NotFoundException {
        List<Product> productList = productRepository.findAll();
        List<ProductCardResponse> productCardResponses = productList.stream()
                .filter(p -> p.getCategory().getCategoryName().equalsIgnoreCase(categoryName))
                .filter(p -> subCategoryName == null || p.getSubCategory().getSubCategoryName().equalsIgnoreCase(subCategoryName))
                .filter(p -> minPrice == null || p.getProductPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getProductPrice() <= maxPrice)
                .filter(p -> colors == null || colors.isEmpty() || colors.stream().map(String::toLowerCase).toList().contains(p.getColor().toLowerCase()))
                .filter(p -> memory == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Ноутбуки") &&
                        p.getVideoCardMemory() == memory.byteValue()) || (p.getCategory().getCategoryName().equalsIgnoreCase("Планшеты") &&
                        p.getMemoryOfTablet() == memory) ||
                        (p.getCategory().getCategoryName().equalsIgnoreCase("Смартфоны") && p.getMemoryOfPhone() == memory) ||
                        (p.getCategory().getCategoryName().equalsIgnoreCase("Смарт-часы и браслеты") && p.getMemoryOfSmartWatch() == memory))
                .filter(p -> ram == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Смартфоны") && p.getRamOfPhone() == ram) ||
                        (p.getCategory().getCategoryName().equalsIgnoreCase("Ноутбуки") && Objects.equals(p.getRamOfLaptop(), ram)) ||
                        (p.getCategory().getCategoryName().equalsIgnoreCase("Планшеты") && p.getRamOfTablet() == ram))
                .map(p -> new ProductCardResponse(p.getId(),
                        p.getProductName(),
                        p.getProductCount(),
                        p.getProductPrice(),
                        p.getProductStatus(),
                        p.getProductRating()))
                .collect(Collectors.toList());
        for (ProductCardResponse productCardResponse : productCardResponses) {
            Optional<Product> productOptional = productRepository.findById(productCardResponse.getProductId());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                if (product.getCategory().getCategoryName().equalsIgnoreCase(categoryName)) {
                    int countFeedback = product.getUsersReviews().size();
                    productCardResponse.setCountOfReview(countFeedback);
                    productCardResponse.setProductImage(productRepository.getFirstImage(productCardResponse.getProductId()));
                    setDiscountToResponse(productCardResponse, null);
                }
            } else {
                throw new NotFoundException();
            }
        }

        if (fieldToSort != null)
            productCardResponses = sortingProduct2(fieldToSort, discountField, productCardResponses);

        return productCardResponses;
    }

    private List<ProductCardResponse> sortingProduct2(String fieldToSort, String discountField, List<ProductCardResponse> productCardResponses) {
        if (fieldToSort != null) {
            switch (fieldToSort) {
                case "Новинки" ->
                        productCardResponses = productCardResponses.stream().filter(x -> x.getProductStatus() == ProductStatus.NEW).toList();
                case "По акции" -> {
                    if (discountField != null) {
                        switch (discountField) {
                            case "Все акции" ->
                                    productCardResponses = productCardResponses.stream().filter(x -> (x.getDiscountPrice() * 100) / x.getProductPrice() > 0).toList();
                            case "До 50%" ->
                                    productCardResponses = productCardResponses.stream().filter(x -> (x.getDiscountPrice() * 100) / x.getProductPrice() < 50 && (x.getDiscountPrice() * 100) / x.getProductPrice() > 0).toList();
                            case "Свыше 50%" ->
                                    productCardResponses = productCardResponses.stream().filter(x -> (x.getDiscountPrice() * 100) / x.getProductPrice() > 50).toList();
                        }
                    }
                }
                case "Рекомендуемые" ->
                        productCardResponses = productCardResponses.stream().filter(x -> x.getProductStatus() == ProductStatus.RECOMMENDATION).toList();
                case "По увеличению цены" ->
                        productCardResponses.sort(Comparator.comparing(ProductCardResponse::getProductPrice));
                case "По уменьшению цены" ->
                        productCardResponses.sort(Comparator.comparing(ProductCardResponse::getProductPrice).reversed());
            }
        }
        return productCardResponses;
    }
}

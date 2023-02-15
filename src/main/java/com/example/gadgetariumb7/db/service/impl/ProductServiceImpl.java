package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.*;
import com.example.gadgetariumb7.db.enums.ProductStatus;
import com.example.gadgetariumb7.db.repository.*;
import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.InforgraphicsResponse;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.response.ProductAdminPaginationResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.BadRequestException;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.AopInvocationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final OrderRepository orderRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public List<ProductCardResponse> getAllDiscountProductToMP(int page, int size) {
        List<ProductCardResponse> discountProducts = productRepository.getAllDiscountProduct(PageRequest.of(page - 1, size));
        discountProducts.forEach(r -> {
            r.setDiscountPrice(productRepository.getDiscountPrice(r.getProductId()));
            r.setCountOfReview(productRepository.getAmountOfFeedback(r.getProductId()));
        });
        return discountProducts;
    }

    @Override
    public List<ProductCardResponse> getAllNewProductToMP(int page, int size) {
        List<ProductCardResponse> newProducts = productRepository.getAllNewProduct(PageRequest.of(page - 1, size));
        newProducts.forEach(r -> {
            setDiscountToResponse(r, null);
            r.setCountOfReview(productRepository.getAmountOfFeedback(r.getProductId()));
        });
        return newProducts;
    }

    @Override
    public List<ProductCardResponse> getAllRecommendationProductToMP(int page, int size) {
        List<ProductCardResponse> recommendations = productRepository.getAllRecommendationProduct(PageRequest.of(page - 1, size));
        recommendations.forEach(r -> {
            setDiscountToResponse(r, null);
            r.setCountOfReview(productRepository.getAmountOfFeedback(r.getProductId()));
        });
        return recommendations;
    }

    @Override
    public ProductAdminPaginationResponse getProductAdminResponses(String searchText, String productType, String fieldToSort, String discountField, LocalDate startDate, LocalDate endDate, int page, int size) {
        ProductAdminPaginationResponse productAdminPaginationResponse = new ProductAdminPaginationResponse();
        List<ProductAdminResponse> productAdminResponses;
        if (searchText == null) {
            productAdminResponses = sortingProduct(fieldToSort, discountField, productRepository.getAllProductsAdmin(PageRequest.of(page - 1, size)), startDate, endDate);
            int number = sortingProduct(fieldToSort, discountField, productRepository.getAllProductsAdminWithoutPagination(), startDate, endDate).size();
            productAdminPaginationResponse.setResponseList(productAdminResponses);
            productAdminPaginationResponse.setPages(number / size);
            productAdminPaginationResponse.setCurrentPage(page);
        } else {
            productAdminResponses = sortingProduct(fieldToSort, discountField, productRepository.search(searchText, PageRequest.of(page - 1, size)), startDate, endDate);
            productAdminPaginationResponse.setResponseList(productAdminResponses);
            productAdminPaginationResponse.setPages(productRepository.searchCount(searchText) / size);
            productAdminPaginationResponse.setCurrentPage(page);
        }
        switch (productType) {
            case "Все товары" -> {
                productAdminPaginationResponse.setResponseList(productAdminResponses);
                return productAdminPaginationResponse;
            }
            case "В продаже" -> {
                productAdminResponses = productAdminResponses.stream().filter(x -> x.getProductCount() > 0).toList();
                productAdminPaginationResponse.setResponseList(productAdminResponses);
                return productAdminPaginationResponse;
            }
            case "В корзине" -> {
                List<Product> productList = new ArrayList<>();
                List<ProductAdminResponse> responseList = new ArrayList<>();

                userRepository.findAll().stream().filter(u -> u.getBasketList() != null).forEach(x -> x.getBasketList().keySet().stream().map(Subproduct::getProduct).filter(product -> !productList.contains(product)).forEach(productList::add));
                productList.forEach(p -> responseList.add(new ProductAdminResponse(p.getId(), p.getProductImage(), p.getProductVendorCode(), p.getProductName(), p.getProductCount(), p.getSubproducts().size(), p.getCreateAt(), p.getProductPrice(), p.getProductStatus())));
                productAdminResponses = sortingProduct(fieldToSort, discountField, responseList, startDate, endDate);
                productAdminPaginationResponse.setResponseList(productAdminResponses);
                return productAdminPaginationResponse;
            }
            case "В избранном" -> {
                List<Product> productList = new ArrayList<>();
                List<ProductAdminResponse> responseList = new ArrayList<>();

                userRepository.findAll().stream().filter(u -> u.getFavoritesList() != null).forEach(x -> x.getFavoritesList().stream().filter(p -> !productList.contains(p)).forEach(productList::add));
                productList.forEach(p -> responseList.add(new ProductAdminResponse(p.getId(), p.getProductImage(), p.getProductVendorCode(), p.getProductName(), p.getProductCount(), p.getSubproducts().size(), p.getCreateAt(), p.getProductPrice(), p.getProductStatus())));

                productAdminResponses = sortingProduct(fieldToSort, discountField, responseList, startDate, endDate);
                productAdminPaginationResponse.setResponseList(productAdminResponses);
                return productAdminPaginationResponse;
            }
        }
        return productAdminPaginationResponse;
    }

    @Override
    public SimpleResponse delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product for delete not found!"));
        userRepository.findAll().forEach(x -> {
            x.getFavoritesList().remove(product);
            product.getSubproducts().forEach(i -> x.getBasketList().remove(i));
            x.getCompareProductsList().remove(product);
            x.getViewedProductsList().remove(product);
        });
        productRepository.delete(product);
        return new SimpleResponse("Product successfully deleted!", "ok");
    }

    @Override
    public SimpleResponse update(Long id, Long vendorCode, Integer productCount, Integer productPrice) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product for update not found!"));
        if (vendorCode != null) product.setProductVendorCode(vendorCode);
        if (productCount != null) product.setProductCount(productCount);
        if (productPrice != null) product.setProductPrice(productPrice);
        productRepository.save(product);
        return new SimpleResponse("Product successfully updated", "ok");
    }

    private List<ProductAdminResponse> sortingProduct(String fieldToSort, String discountField, List<ProductAdminResponse> products, LocalDate startDate, LocalDate endDate) {
        products.forEach(r -> setDiscountToResponse(null, r));

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
        Subcategory subcategory = subcategoryRepository.findById(productRequest.getSubCategoryId()).orElseThrow(() -> new NotFoundException("Subcategory not found"));

        List<Subproduct> subproducts = new ArrayList<>();
        productRequest.getSubProductRequests().forEach(x -> subproducts.add(new Subproduct(x)));

        Product product = new Product(productRequest, subproducts, brand, category, subcategory);
        product.setCreateAt(LocalDateTime.now());
        product.setProductStatus(ProductStatus.NEW);
        product.setSubproducts(subproducts);
        subproducts.forEach(s -> s.setProduct(product));
        productRepository.save(product);

        return new SimpleResponse("Product successfully saved", "ok");
    }

    @Override
    public List<ProductCardResponse> filterByParameters(String text, String categoryName, String fieldToSort, String discountField, String subCategoryName, Integer minPrice, Integer maxPrice, List<String> colors, Integer memory, Byte ram, int size) throws NotFoundException {
        if (text != null) {
            List<ProductCardResponse> list = productRepository.searchCatalog(text, PageRequest.of(0, size)).stream()
                    .map(p -> new ProductCardResponse(p.getId(),
                            p.getProductImage(),
                            p.getProductName(),
                            p.getProductCount(),
                            p.getProductPrice(),
                            p.getProductStatus(),
                            p.getProductRating()))
                    .collect(Collectors.toList());
            forEach(list);
            return list;
        }
        List<Product> productList = productRepository.findAll();
        List<ProductCardResponse> productCardResponses = productList.stream()
                .filter(p -> categoryName == null || p.getCategory().getCategoryName().equalsIgnoreCase(categoryName))
                .filter(p -> subCategoryName == null || p.getSubCategory().getSubCategoryName().equalsIgnoreCase(subCategoryName))
                .filter(p -> minPrice == null || p.getProductPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getProductPrice() <= maxPrice)
                .filter(p -> colors == null || colors.isEmpty() || colors.stream().map(String::toLowerCase).toList().contains(p.getColor().toLowerCase()))
                .filter(p -> memory == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Ноутбуки") &&
                        Byte.parseByte(p.getSubproducts().get(0).getCharacteristics().get("videoCardMemory")) == memory.byteValue()) || (p.getCategory().getCategoryName().equalsIgnoreCase("Планшеты") &&
                        Byte.parseByte(p.getSubproducts().get(0).getCharacteristics().get("memoryOfTablet")) == memory) ||
                        (p.getCategory().getCategoryName().equalsIgnoreCase("Смартфоны") && Integer.parseInt(p.getSubproducts().get(0).getCharacteristics().get("memoryOfPhone")) == memory) ||
                        (p.getCategory().getCategoryName().equalsIgnoreCase("Смарт-часы и браслеты") && Integer.parseInt(p.getSubproducts().get(0).getCharacteristics().get("memoryOfSmartWatch")) == memory))
                .filter(p -> ram == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Смартфоны") && Integer.parseInt(p.getSubproducts().get(0).getCharacteristics().get("ramOfPhone")) == ram) ||
                        (p.getCategory().getCategoryName().equalsIgnoreCase("Ноутбуки") && Objects.equals(Byte.parseByte(p.getSubproducts().get(0).getCharacteristics().get("ramOfLaptop")), ram)) ||
                        (p.getCategory().getCategoryName().equalsIgnoreCase("Планшеты") && Byte.parseByte(p.getSubproducts().get(0).getCharacteristics().get("ramOfTablet")) == ram))
                .map(p -> new ProductCardResponse(p.getId(),
                        p.getProductImage(),
                        p.getProductName(),
                        p.getProductCount(),
                        p.getProductPrice(),
                        p.getProductStatus(),
                        p.getProductRating()))
                .collect(Collectors.toList());

        int toIndex = Math.min(size, productCardResponses.size());
        productCardResponses = productCardResponses.subList(0, toIndex);

        forEach(productCardResponses);

        if (fieldToSort != null) {
            productCardResponses = sortingProduct2(fieldToSort, discountField, productCardResponses);
        }
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
                                    productCardResponses = productCardResponses.stream().filter(x -> 100 - ((x.getDiscountPrice() * 100) / (x.getProductPrice())) > 0).toList();
                            case "До 50%" ->
                                    productCardResponses = productCardResponses.stream().filter(x -> (100 - (((x.getDiscountPrice()) * 100) / (x.getProductPrice()))) < 50 && (100 - ((x.getDiscountPrice() * 100) / (x.getProductPrice()))) > 0).toList();
                            case "Свыше 50%" ->
                                    productCardResponses = productCardResponses.stream().filter(x -> (100 - ((x.getDiscountPrice() * 100) / (x.getProductPrice()))) > 50 && x.getDiscountPrice() != 0).toList();
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

    public void forEach(List<ProductCardResponse> productCardResponses) {
        for (ProductCardResponse productCardResponse : productCardResponses) {
            User user = getAuthenticateUser();
            Optional<Product> productOptional = productRepository.findById(productCardResponse.getProductId());
            if (productOptional.isPresent()) {
                if (user.getFavoritesList().contains(productOptional.get())) {
                    productCardResponse.setFavorite(true);
                }
                if (user.getCompareProductsList().contains(productOptional.get())) {
                    productCardResponse.setCompared(true);
                }
                Product product = productOptional.get();
                if (product.getUsersReviews() != null) {
                    int countFeedback = product.getUsersReviews().size();
                    productCardResponse.setCountOfReview(countFeedback);
                } else {
                    productCardResponse.setCountOfReview(0);
                }
                setDiscountToResponse(productCardResponse, null);
                if (product.getProductImage() == null) {
                    productCardResponse.setProductImage(productRepository.getFirstImage(product.getId()));
                }
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public InforgraphicsResponse inforgraphics() throws NullPointerException {
        try {
            InforgraphicsResponse inforgraphics = new InforgraphicsResponse();
            inforgraphics.setSoldCount(productRepository.getCountSoldProducts());
            inforgraphics.setSoldPrice(productRepository.getSoldProductPrice());
            inforgraphics.setOrderCount(productRepository.getCountOrderProduct());
            inforgraphics.setOrderPrice(productRepository.getOrderProductPrice());
            inforgraphics.setCurrentPeriodPerDay(productRepository.getCurrentPeriodPerDay());
            inforgraphics.setCurrentPeriodPerMonth(productRepository.getCurrentPeriodPerMonth());
            inforgraphics.setCurrentPeriodPerYear(productRepository.getCurrentPeriodPerYear());
            inforgraphics.setPreviousPeriodPerDay(productRepository.getPreviousPeriodPerDay());
            inforgraphics.setPreviousPeriodPerMonth(productRepository.getPreviousPeriodPerMonth());
            inforgraphics.setPreviousPeriodPerYear(productRepository.getPreviousPeriodPerYear());
            return inforgraphics;

        } catch (AopInvocationException e) {
            throw new BadRequestException("Inforgraphic is null");
        }
    }

}

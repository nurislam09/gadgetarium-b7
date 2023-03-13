package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.*;
import com.example.gadgetariumb7.db.enums.ProductStatus;
import com.example.gadgetariumb7.db.repository.*;
import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.converter.ColorNameMapper;
import com.example.gadgetariumb7.dto.response.InforgraphicsResponse;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.request.ProductUpdateRequest;
import com.example.gadgetariumb7.dto.request.SubproductUpdateRequest;
import com.example.gadgetariumb7.dto.response.*;
import com.example.gadgetariumb7.exceptions.BadRequestException;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AopInvocationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SubproductRepository subproductRepository;
    private final ColorNameMapper colorNameMapper;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("the token has taken successfully");
        return userRepository.findByEmail(login).orElseThrow(() -> {
            log.error("User not found");
            throw new NotFoundException("User not found!");
        });
    }

    private Optional<User> getAuthenticateUserForFavorite() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("The user token for favorite has taken");
        return userRepository.findByEmail(login);
    }

    @Override
    public List<ProductCardResponse> getAllDiscountProductToMP(int page, int size) {
        List<ProductCardResponse> discountProducts = productRepository.getAllDiscountProduct(PageRequest.of(page - 1, size));
        discountProducts.forEach(p -> p.setCategoryId(productRepository.findById(p.getProductId()).orElseThrow(() -> new NotFoundException("Product not found")).getCategory().getId().byteValue()));
        log.info("all discount product taken to main page successfully");
        return checkFavorite(discountProducts);
    }

    @Override
    public List<ProductCardResponse> getAllNewProductToMP(int page, int size) {
        List<ProductCardResponse> newProducts = productRepository.getAllNewProduct(PageRequest.of(page - 1, size));
        newProducts.forEach(p -> p.setCategoryId(productRepository.findById(p.getProductId()).orElseThrow(() -> new NotFoundException("Product not found")).getCategory().getId().byteValue()));
        log.info("all new product taken to main page successfully");
        return checkFavorite(newProducts);
    }

    @Override
    public List<ProductCardResponse> getAllRecommendationProductToMP(int page, int size) {
        List<ProductCardResponse> recommendations = productRepository.getAllRecommendationProduct(PageRequest.of(page - 1, size));
        recommendations.forEach(p -> p.setCategoryId(productRepository.findById(p.getProductId()).orElseThrow(() -> new NotFoundException("Product not found")).getCategory().getId().byteValue()));
        log.info("all recommendation product taken to main page successfully");
        return checkFavorite(recommendations);
    }

    private List<ProductCardResponse> checkFavorite(List<ProductCardResponse> productCardResponses) {
        productCardResponses.forEach(r -> {
            setDiscountToResponse(r, null);
            r.setCountOfReview(productRepository.getAmountOfFeedback(r.getProductId()));
        });
        if (getAuthenticateUserForFavorite().isPresent()) {
            User user = getAuthenticateUserForFavorite().get();
            productCardResponses.forEach(x -> {
                Optional<Product> productOptional = productRepository.findById(x.getProductId());
                if (productOptional.isPresent()) {
                    if (user.getFavoritesList().contains(productOptional.get())) {
                        x.setFavorite(true);
                    }
                    if (user.getCompareProductsList().contains(productOptional.get())) {
                        x.setCompared(true);
                    }
                } else {
                    log.error("Product not found");
                    throw new NotFoundException("Product not found!");
                }
            });
        }
        log.info("favorite has checked successfully");
        return productCardResponses;
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
        if (productType != null) {
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
                default -> {
                    log.error("Product type is not correct");
                    throw new BadRequestException("Product type is not correct");
                }
            }
        }
        log.info("admin product is delivered");
        return productAdminPaginationResponse;
    }

    @Override
    public SimpleResponse delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.error("Product for delete not found");
            throw new NotFoundException("Product for delete not found!");
        });
        userRepository.findAll().forEach(x -> {
            x.getFavoritesList().remove(product);
            product.getSubproducts().forEach(i -> x.getBasketList().remove(i));
            x.getCompareProductsList().remove(product);
            x.getViewedProductsList().remove(product);
        });
        productRepository.delete(product);
        log.info("successfully works the delete method");
        return new SimpleResponse("Product successfully deleted!", "ok");
    }

    @Override
    public SimpleResponse update(ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productUpdateRequest.getId()).orElseThrow(() -> {
            log.error("Product for update not found");
            throw new NotFoundException("Product for update not found!");
        });
        List<Subproduct> subproducts = product.getSubproducts();
        List<SubproductUpdateRequest> subproductUpdateRequests = new ArrayList<>(productUpdateRequest.getSubproductUpdateRequests());
        for (SubproductUpdateRequest s : subproductUpdateRequests) {
            Subproduct subproduct = subproductRepository.findById(s.getId()).orElseThrow(() -> {
                log.error("Subproduct for update not found!");
                throw new NotFoundException("Subproduct for update not found!");
            });
            int index = subproducts.indexOf(subproduct);
            if (index != -1) {
                if (s.getSubproductCount() != 0) subproduct.setCountOfSubproduct(s.getSubproductCount());
                if (s.getPrice() != 0) subproduct.setPrice(s.getPrice());
                subproducts.set(index, subproduct);
            }

            if (index == 0) {
                product.setProductPrice(subproduct.getPrice());
                product.setProductCount(subproduct.getCountOfSubproduct());
            }
        }
        product.setSubproducts(subproducts);
        productRepository.save(product);
        log.info("successfully works the update method");
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
                            default -> throw new BadRequestException("Discount sort field is not correct");
                        }
                    }
                }
                case "Рекомендуемые" ->
                        products = products.stream().filter(x -> x.getProductStatus() == ProductStatus.RECOMMENDATION).toList();
                case "По увеличению цены" -> products.sort(Comparator.comparing(ProductAdminResponse::getProductPrice));
                case "По уменьшению цены" ->
                        products.sort(Comparator.comparing(ProductAdminResponse::getProductPrice).reversed());
                default -> {
                    log.error("Sort field is not correct");
                    throw new BadRequestException("Sort field is not correct");
                }
            }
        }
        if (startDate != null && endDate != null)
            return products.stream().filter(p -> p.getCreateAt().toLocalDate().isAfter(startDate) && p.getCreateAt().toLocalDate().isBefore(endDate)).toList();
        log.info("successfully works the sorting product method");
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
            log.error("null discount");
        }
        log.info("successfully works the setDiscountResponse");
    }

    @Override
    public SimpleResponse addProduct(ProductRequest productRequest) {
        Brand brand = brandRepository.findById(productRequest.getBrandId()).orElseThrow(() -> {
            log.error("Brand not found");
            throw new NotFoundException("Brand not found");
        });
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> {
            log.error("Category not found");
            throw new NotFoundException("Category not found");
        });
        Subcategory subcategory = subcategoryRepository.findById(productRequest.getSubCategoryId()).orElseThrow(() -> {
            log.error("Subcategory not found");
            throw new NotFoundException("Subcategory not found");
        });
        List<Subproduct> subproducts = new ArrayList<>();
        productRequest.getSubProductRequests().forEach(x -> subproducts.add(new Subproduct(x)));

        Product product = new Product(productRequest, subproducts, brand, category, subcategory);
        product.setCreateAt(LocalDateTime.now());
        product.setProductStatus(ProductStatus.NEW);
        product.setSubproducts(subproducts);
        product.setDateOfIssue(productRequest.getDateOfIssue());
        subproducts.forEach(s -> s.setProduct(product));
        productRepository.save(product);
        log.info("successfully works the add product method");
        return new SimpleResponse("Product successfully saved", "ok");
    }

    @Override
    public CatalogResponse filterByParameters(String text, String fieldToSort, String discountField, String categoryName, String subCategoryName, Integer minPrice, Integer maxPrice, List<String> colors,
                                                        Integer memory, Byte ram, String laptopCPU, String screenResolution, String screenSize, String screenDiagonal, String batteryCapacity,
                                                        String wirelessInterface, String caseShape, String braceletMaterial, String housingMaterial, String gender, String waterProof, int size) throws NotFoundException {
        try {
            CatalogResponse catalogResponse = new CatalogResponse();

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

                catalogResponse.setProductCardResponses(list);
                catalogResponse.setSizeOfProducts(list.size());
                return catalogResponse;
            }

            List<Product> productList = productRepository.findAll();
            List<ProductCardResponse> productCardResponses = productList.stream()
                    .filter(p -> categoryName == null || p.getCategory().getCategoryName().equalsIgnoreCase(categoryName))
                    .filter(p -> subCategoryName == null || p.getSubCategory().getSubCategoryName().equalsIgnoreCase(subCategoryName))
                    .filter(p -> minPrice == null || p.getProductPrice() >= minPrice)
                    .filter(p -> maxPrice == null || p.getProductPrice() <= maxPrice)
                    .filter(p -> colors == null || colors.isEmpty() || colors.stream().map(String::toLowerCase).toList().contains(p.getColor().toLowerCase()))
                    .filter(p -> memory == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Ноутбуки") &&
                            p.getSubproducts().get(0).getCharacteristics().get("videoCardMemory").equals(memory.toString())) || (p.getCategory().getCategoryName().equalsIgnoreCase("Планшеты") &&
                            p.getSubproducts().get(0).getCharacteristics().get("memoryOfTablet").equals(memory.toString())) ||
                            (p.getCategory().getCategoryName().equalsIgnoreCase("Смартфоны") && p.getSubproducts().get(0).getCharacteristics().get("memoryOfPhone").equalsIgnoreCase(memory.toString())) ||
                            (p.getCategory().getCategoryName().equalsIgnoreCase("Смарт-часы и браслеты") && p.getSubproducts().get(0).getCharacteristics().get("memoryOfSmartWatch").equalsIgnoreCase(memory.toString())))
                    .filter(p -> ram == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Смартфоны") && p.getSubproducts().get(0).getCharacteristics().get("ramOfPhone").equalsIgnoreCase(ram.toString())) ||
                            (p.getCategory().getCategoryName().equalsIgnoreCase("Ноутбуки") && p.getSubproducts().get(0).getCharacteristics().get("ramLaptop").equalsIgnoreCase(ram.toString())) ||
                            (p.getCategory().getCategoryName().equalsIgnoreCase("Планшеты") && p.getSubproducts().get(0).getCharacteristics().get("ramOfTablet").equalsIgnoreCase(ram.toString())))
                    .filter(p -> laptopCPU == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Ноутбуки") &&
                            p.getSubproducts().get(0).getCharacteristics().get("laptopCPU").equalsIgnoreCase(laptopCPU)))
                    .filter(p -> screenResolution == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Ноутбуки") &&
                            p.getSubproducts().get(0).getCharacteristics().get("screenResolutionLaptop").equalsIgnoreCase(screenResolution)) ||
                            (p.getCategory().getCategoryName().equalsIgnoreCase("Планшеты") && p.getSubproducts().get(0).getCharacteristics().get("screenResolutionOfTablet").equalsIgnoreCase(screenResolution)))
                    .filter(p -> screenSize == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Ноутбуки") &&
                            p.getSubproducts().get(0).getCharacteristics().get("screenSizeLaptop").equalsIgnoreCase(screenSize)) ||
                            (p.getCategory().getCategoryName().equalsIgnoreCase("Планшеты") && p.getSubproducts().get(0).getCharacteristics().get("screenSizeOfTablet").equalsIgnoreCase(screenSize)))
                    .filter(p -> screenDiagonal == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Планшеты") &&
                            p.getSubproducts().get(0).getCharacteristics().get("screenDiagonalOfTablet").equalsIgnoreCase(screenDiagonal)) ||
                            (p.getCategory().getCategoryName().equalsIgnoreCase("Смарт-часы и браслеты") &&
                                    p.getSubproducts().get(0).getCharacteristics().get("screenDiagonalOfSmartWatch").equalsIgnoreCase(screenDiagonal)))
                    .filter(p -> batteryCapacity == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Планшеты") &&
                            p.getSubproducts().get(0).getCharacteristics().get("batteryCapacityOfTablet").equalsIgnoreCase(batteryCapacity)))
                    .filter(p -> wirelessInterface == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Смарт-часы и браслеты") &&
                            p.getSubproducts().get(0).getCharacteristics().get("wirelessInterface").equalsIgnoreCase(wirelessInterface)))
                    .filter(p -> caseShape == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Смарт-часы и браслеты") &&
                            p.getSubproducts().get(0).getCharacteristics().get("caseShape").equalsIgnoreCase(caseShape)))
                    .filter(p -> braceletMaterial == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Смарт-часы и браслеты") &&
                            p.getSubproducts().get(0).getCharacteristics().get("braceletMaterial").equalsIgnoreCase(braceletMaterial)))
                    .filter(p -> housingMaterial == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Смарт-часы и браслеты") &&
                            p.getSubproducts().get(0).getCharacteristics().get("housingMaterial").equalsIgnoreCase(housingMaterial)))
                    .filter(p -> gender == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Смарт-часы и браслеты") &&
                            p.getSubproducts().get(0).getCharacteristics().get("gender").equalsIgnoreCase(gender)))
                    .filter(p -> waterProof == null || (p.getCategory().getCategoryName().equalsIgnoreCase("Смарт-часы и браслеты") &&
                            p.getSubproducts().get(0).getCharacteristics().get("waterProof").equalsIgnoreCase(waterProof)))
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

            catalogResponse.setProductCardResponses(productCardResponses);
            catalogResponse.setSizeOfProducts(productCardResponses.size());
            log.info("successfully works the filter by parameters method");
            return catalogResponse;
        } catch (NotFoundException | NullPointerException e) {
            log.error("Product not found");
            throw new NotFoundException("Product not found");
        }
    }

    @Override
    public List<ColorResponse> colorCount(Long categoryId) {
        List<Long> productsId = productRepository.getCategoryProducts(categoryId);
        List<ColorResponse> colorResponses = new ArrayList<>();
        for (Long x: productsId) {
            Product product = productRepository.findById(x).orElseThrow(() -> new NotFoundException("Product not found"));
            if(colorResponses.isEmpty() || colorResponses.stream().noneMatch(c -> c.getColorName().equalsIgnoreCase(colorNameMapper.getColorName(product.getColor())))){
                colorResponses.add(new ColorResponse(product.getColor(), colorNameMapper.getColorName(product.getColor()), (int) productsId.stream().filter(p -> productRepository.findById(p).orElseThrow(() -> new NotFoundException("Product not found")).getColor().equals(product.getColor())).count()));
            }
        }
        return colorResponses;
    }

    @Override
    public List<ProductCardResponse> getViewedProducts() {
        List<Long> productsId = productRepository.getViewedProducts(getAuthenticateUser().getId());
        List<ProductCardResponse> responses = new ArrayList<>();
        productsId.forEach(r -> {
            Product p = productRepository.findById(r).orElseThrow(() -> {
                log.error("Product not found");
                throw new NotFoundException("Product not found");
            });
            responses.add(new ProductCardResponse(p.getId(), p.getProductName(), p.getProductImage(), p.getProductRating(),
                    productRepository.getAmountOfFeedback(p.getId()), p.getProductPrice()));
        });
        responses.forEach(p -> p.setCategoryId(productRepository.findById(p.getProductId()).orElseThrow(() -> new NotFoundException("Product not found")).getCategory().getId().byteValue()));
        log.info("successfully works the getViewedProducts");
        return responses;

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
                                    productCardResponses = productCardResponses.stream().filter(x -> 100 - ((x.getDiscountPrice() * 100) / (x.getProductPrice())) > 0 && x.getDiscountPrice() != 0).toList();
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
        log.info("successfully works the sortingProduct2 method");
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
            } else {
                log.error("Not found product card response");
                throw new NotFoundException();
            }
        }
        log.info("successfully works the for each method");
    }

    @Override
    public InforgraphicsResponse infographics() throws NullPointerException {
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
            log.info("successfully works the infographics method");
            return inforgraphics;
        } catch (AopInvocationException e) {
            log.error("Infographic is null");
            throw new BadRequestException("Inforgraphic is null");
        }
    }


    @Override
    public ProductSingleResponse getProductById(Long productId, String attribute, Integer size) {
        User user = getAuthenticateUser();
        ProductSingleResponse productSingleResponse;
        Product p = productRepository.findById(productId).orElseThrow(() -> {
            log.error("We don't have the product with such id");
            throw new NotFoundException("we don't have the product with such id");
        });
        List<SubproductResponse> subproducts = p.getSubproducts().stream().map(s -> new SubproductResponse(s.getId(), s.getCountOfSubproduct(),
                s.getImages(), s.getPrice(), colorNameMapper.getColorName(s.getColor()), s.getColor(), s.getCharacteristics())).toList();
        productSingleResponse = new ProductSingleResponse(p.getId(), p.getProductName(), p.getProductCount(),
                p.getProductVendorCode(), p.getCategory().getCategoryName(), p.getSubCategory().getSubCategoryName(),
                p.getUsersReviews().size(), p.getProductPrice(), p.getProductRating(), subproducts);
        try {
            productSingleResponse.setAmountOfDiscount(p.getDiscount().getAmountOfDiscount());
        } catch (RuntimeException e) {
            log.error("null discount");
        }
        if (user.getFavoritesList().contains(p)) {
            productSingleResponse.setFavorite(true);
        }
        switch (attribute) {
            case "Описание" -> {
                String description = p.getDescription();
                productSingleResponse.setAttribute("Описание", description);
                productSingleResponse.setVideoReview(p.getVideoReview());
            }
            case "Характеристики" -> {
                Map<String, String> characteristics = p.getSubproducts().get(0).getCharacteristics();
                productSingleResponse.setAttribute("Характеристики", characteristics);
            }
            case "Отзывы" -> {
                List<Review> reviews = p.getUsersReviews();
                List<ReviewMainResponse> reviewMainResponses = reviews.stream().map(r -> new ReviewMainResponse(
                        r.getId(), r.getUserReview(), r.getResponseOfReview(),
                        r.getReviewTime(), r.getProductGrade(), new UserMainResponse(
                        r.getUser().getId(), r.getUser().getFirstName() + " " + r.getUser().getLastName(),
                        r.getUser().getImage())
                )).toList();
                if (size == null) {
                    size = 3;
                }
                int toIndex = Math.min(size, reviewMainResponses.size());
                reviewMainResponses = reviewMainResponses.subList(0, toIndex);
                Map<Integer, Integer> counts = new HashMap<>();
                for (int i = 1; i < 6; i++) {
                    counts.put(i, reviewRepository.getCountReviewOfProduct(productSingleResponse.getId(), i));
                }
                productSingleResponse.setReviewCount(counts);
                productSingleResponse.setAttribute("Отзывы", reviewMainResponses);
            }
        }
        user.addViewedProduct(p);
        if (user.getUserReviews().stream().anyMatch(x -> Objects.equals(x.getProduct().getId(), p.getId())))
            productSingleResponse.setReviewed(true);
        userRepository.save(user);
        log.info("successfully works the productSingleResponse method");
        return productSingleResponse;
    }
}

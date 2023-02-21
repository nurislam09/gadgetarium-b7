package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.*;
import com.example.gadgetariumb7.db.enums.ProductStatus;
import com.example.gadgetariumb7.db.repository.*;
import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.request.ProductUpdateRequest;
import com.example.gadgetariumb7.dto.request.SubproductUpdateRequest;
import com.example.gadgetariumb7.dto.response.*;
import com.example.gadgetariumb7.exceptions.BadRequestException;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
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
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SubproductRepository subproductRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    private Optional<User> getAuthenticateUserForFavorite() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

    @Override
    public List<ProductCardResponse> getAllDiscountProductToMP(int page, int size) {
        List<ProductCardResponse> discountProducts = productRepository.getAllDiscountProduct(PageRequest.of(page - 1, size));
        return checkFavorite(discountProducts);
    }

    @Override
    public List<ProductCardResponse> getAllNewProductToMP(int page, int size) {
        List<ProductCardResponse> newProducts = productRepository.getAllNewProduct(PageRequest.of(page - 1, size));
        return checkFavorite(newProducts);
    }

    @Override
    public List<ProductCardResponse> getAllRecommendationProductToMP(int page, int size) {
        List<ProductCardResponse> recommendations = productRepository.getAllRecommendationProduct(PageRequest.of(page - 1, size));
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
                    throw new NotFoundException("Product not found!");
                }
            });
        }

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
                default -> throw new BadRequestException("Product type is not correct");
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
    public SimpleResponse update(ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productUpdateRequest.getId()).orElseThrow(() -> new NotFoundException("Product for update not found!"));
        List<Subproduct> subproducts = product.getSubproducts();
        List<SubproductUpdateRequest> subproductUpdateRequests = new ArrayList<>(productUpdateRequest.getSubproductUpdateRequests());
        for (SubproductUpdateRequest s : subproductUpdateRequests) {
            Subproduct subproduct = subproductRepository.findById(s.getId()).orElseThrow(() -> new NotFoundException("Subproduct for update not found!"));
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
                default -> throw new BadRequestException("Sort field is not correct");
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
        productRequest.getSubProductRequests().forEach(x -> {
            subproducts.add(new Subproduct(x));
        });

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
                throw new NotFoundException();
            }
        }
    }

    @Override
    public ProductSingleResponse getProductById(Long productId, String attribute, Integer size) {
        User user = getAuthenticateUser();
        ProductSingleResponse productSingleResponse;
        Product p = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("we don't have the product with such id"));
            List<SubproductResponse> subproducts = p.getSubproducts().stream().map(s -> new SubproductResponse(s.getId(), s.getCountOfSubproduct(),
                    s.getImages(), s.getPrice(), s.getColor(), s.getCharacteristics())).toList();
            productSingleResponse = new ProductSingleResponse(p.getId(), p.getProductName(), p.getProductCount(),
                    p.getProductVendorCode(), p.getCategory().getCategoryName(), p.getSubCategory().getSubCategoryName(),
                    p.getUsersReviews().size(), p.getProductPrice(), p.getProductRating(), p.getColor(), subproducts);
            try{
            productSingleResponse.setAmountOfDiscount(p.getDiscount().getAmountOfDiscount());}
            catch (RuntimeException e){
                System.out.println("null discount");
            }
        if (user.getFavoritesList().contains(p)) {
            productSingleResponse.setFavorite(true);
        }
        switch (attribute) {
            case "Описание" -> {
                String description = p.getDescription();
                productSingleResponse.setAttribute("Описание", description);
                productSingleResponse.setVideoReview(p.getVideoReview());
                return productSingleResponse;
            }
            case "Характеристики" -> {
                Map<String, String> characteristics = p.getSubproducts().get(0).getCharacteristics();
                productSingleResponse.setAttribute("Характеристики", characteristics);
                return productSingleResponse;
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
                productSingleResponse.setAttribute("Отзывы", reviewMainResponses);
                return productSingleResponse;
            }
        }
        return productSingleResponse;
    }
}

package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.Review;
import com.example.gadgetariumb7.db.entity.Subproduct;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.repository.*;
import com.example.gadgetariumb7.db.service.UserService;
import com.example.gadgetariumb7.dto.converter.ColorNameMapper;
import com.example.gadgetariumb7.dto.request.ReviewSaveRequest;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.ProductCompareResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.dto.response.SubproductCardResponse;
import com.example.gadgetariumb7.exceptions.BadRequestException;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final SubproductRepository subproductRepository;

    private final ProductRepository productRepository;

    private final ReviewRepository reviewRepository;

    private final ColorNameMapper colorNameMapper;

    @Value("${google.cloud.apiKey}")
    private String googleAPI;


    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("successfully works the get authenticate user method");
        return userRepository.findByEmail(login).orElseThrow(() -> {
            log.error("User not found");
            throw new NotFoundException("User not found!");
        });
    }

    @Override
    public SimpleResponse addAndRemoveToFavorites(Long productId) {
        User user = getAuthenticateUser();
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            log.error("Product not found");
            throw new NotFoundException("Product not found");
        });

        if (user.getFavoritesList() == null) {
            user.setFavoritesList(Arrays.asList(product));
        } else {
            if (user.getFavoritesList().contains(product)) {
                user.getFavoritesList().remove(product);
                userRepository.save(user);
                log.info("Product successfully deleted from User's favorites");
                return new SimpleResponse("Product successfully deleted from User's favorites", "ok");
            } else user.getFavoritesList().add(product);
        }
        userRepository.save(user);
        log.info("Product successfully added from User's favorites");
        return new SimpleResponse("Product successfully added to User's favorites", "ok");
    }

    @Override
    public SimpleResponse addAndRemoveToCompares(Long productId) {
        User user = getAuthenticateUser();
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));

        if (user.getCompareProductsList() == null) {
            user.setCompareProductsList(Arrays.asList(product));
        } else {
            if (user.getCompareProductsList().contains(product)) {
                user.getCompareProductsList().remove(product);
                userRepository.save(user);
                return new SimpleResponse("Product successfully deleted from User's compares", "ok");
            } else user.getCompareProductsList().add(product);
        }
        userRepository.save(user);
        return new SimpleResponse("Product successfully added to User's compares", "ok");
    }


    @Override
    public List<ProductCardResponse> getAllFavorites() {
        User user = getAuthenticateUser();
        List<ProductCardResponse> favorites = new ArrayList<>();
        for (Long productId : userRepository.getAllFavoritesByUserId(user.getId())) {
            ProductCardResponse productCardResponse = productRepository.convertToResponse(productId);
            productCardResponse.setFavorite(true);
            favorites.add(productCardResponse);
        }
        log.info("successfully works the get all favorites method");
        return favorites;
    }

    public SimpleResponse addToBasketList(int orderCount, Long subProductId) {
        User user = getAuthenticateUser();
        Subproduct subproduct = subproductRepository.findById(subProductId).orElseThrow(() -> {
            log.error("Subproduct not found");
            throw new NotFoundException("Subproduct not found");
        });

        if (!user.getBasketList().containsKey(subproduct)) {
            if (orderCount <= subproduct.getCountOfSubproduct()) {
                user.getBasketList().put(subproduct, orderCount);
            } else {
                log.error(String.format("The orderCount(%d) larger > then subProductCount(%d)", orderCount, subproduct.getCountOfSubproduct()));
                throw new BadRequestException(String.format("The orderCount(%d) larger > then subProductCount(%d)", orderCount, subproduct.getCountOfSubproduct()));
            }
        } else {
            log.error(String.format("Subproduct with id %d already exist in basket", subProductId));
            throw new BadRequestException(String.format("Subproduct with id %d already exist in basket", subProductId));
        }

        subproductRepository.save(subproduct);
        userRepository.save(user);
        log.info("successfully works the add to basketList method");
        return new SimpleResponse("Subproduct successfully add to basket list", "ok");
    }

    @Override
    public SimpleResponse deleteFromBasketList(List<Long> productsId) {
        User user = getAuthenticateUser();

        for (Long id : productsId) {
            Subproduct subproduct = subproductRepository.findById(id).orElseThrow(() -> {
                log.error("Subproduct not found");
                throw new NotFoundException("Subproduct not found");
            });
            if (user.getBasketList().containsKey(subproduct)) {
                user.getBasketList().remove(subproduct);
            } else {
                log.error(String.format("Subproduct with id %d is not exist in basket", id));
                throw new BadRequestException(String.format("Subproduct with id %d is not exist in basket", id));
            }
        }

        userRepository.save(user);
        log.info("successfully works the delete from basket list method");
        return new SimpleResponse("Subproduct successfully deleted from basketList", "ok");
    }

    @Override
    public SimpleResponse moveToFavoriteList(List<Long> subProductsId) {
        User user = getAuthenticateUser();
        subProductsId.forEach(x -> {
            Subproduct subproduct = subproductRepository.findById(x).orElseThrow(() -> {
                log.error("Subproduct not found");
                throw new NotFoundException("Subproduct not found");
            });
            Product product = subproduct.getProduct();
            if (user.getBasketList().containsKey(subproduct)) {
                if (!user.getFavoritesList().contains(product)) {
                    if (user.getFavoritesList() == null) {
                        user.setFavoritesList(Arrays.asList(product));
                    } else {
                        user.getFavoritesList().add(product);
                    }
                }
                user.getBasketList().remove(subproduct);
            }
        });
        userRepository.save(user);
        log.info("successfully works move to favorite List");
        return new SimpleResponse("Subproduct successfully moved to favorite list", "ok");
    }

    @Override
    public List<SubproductCardResponse> getAllFromBasketList() {
        User user = getAuthenticateUser();
        List<Long> subproductsId = subproductRepository.getAllFromUserBasketList(user.getId());
        List<SubproductCardResponse> responses = new ArrayList<>();
        Translate translate = TranslateOptions.newBuilder().setApiKey(googleAPI).build().getService();
        subproductsId.forEach(id -> {
            Subproduct s = subproductRepository.findById(id).get();
            Translation translation = translate.translate(colorNameMapper.getColorName(s.getColor()), Translate.TranslateOption.targetLanguage("ru"));
            SubproductCardResponse subproductCardResponse = new SubproductCardResponse(s.getId(), s.getProduct().getProductName(), s.getImages().get(0), s.getCharacteristics(), translation.getTranslatedText(), s.getProduct().getProductRating(), productRepository.getAmountOfFeedback(s.getProduct().getId()), s.getCountOfSubproduct(), s.getProduct().getProductVendorCode(), user.getBasketList().get(s), s.getPrice());
            if (s.getProduct().getDiscount() != null) {
                subproductCardResponse.setAmountOfDiscount(s.getProduct().getDiscount().getAmountOfDiscount());
            }
            responses.add(subproductCardResponse);
        });
        log.info("successfully works the get all from basket list");
        return responses;
    }

    public SimpleResponse addReview(ReviewSaveRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> {
            log.error("Product not found");
            throw new NotFoundException("Product not found");
        });
        User user = getAuthenticateUser();

        if (!user.getOrderHistoryList().contains(product)) {
            log.error("This customer did not purchase this product");
            throw new BadRequestException("This customer did not purchase this product");
        }

        if (product.getUsersReviews() != null) {
            for (Review r : product.getUsersReviews()) {
                if (r.getUser().getId().equals(user.getId())) {
                    log.error("User has alreaady added a review for this product");
                    throw new BadRequestException("User has already added a review for this product");
                }
            }
        }

        Review review = new Review(request.getProductGrade(), request.getReviewComment(), user, product);
        review.setReviewTime(LocalDateTime.now());
        review.setStatusOfResponse(false);

        if (request.getImages() != null) {
            review.setImages(request.getImages());
        }

        if (product.getUsersReviews() != null) {
            product.getUsersReviews().add(review);
            double rating = product.getUsersReviews().stream().mapToDouble(Review::getProductGrade).average().getAsDouble();
            product.setProductRating(Math.round(rating * 100.0) / 100.0);
        } else {
            product.setProductRating(request.getProductGrade());
            product.setUsersReviews(Arrays.asList(review));
        }

        if (user.getUserReviews() != null)
            user.getUserReviews().add(review);
        else
            user.setUserReviews(Arrays.asList(review));

        reviewRepository.save(review);
        productRepository.save(product);
        userRepository.save(user);
        log.info("successfully works the add review method");
        return new SimpleResponse("Review successfully saved", "ok");
    }

    @Override
    public List<ProductCompareResponse> getAllFromUserCompareProductList(String categoryName, int size, int page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        User user = getAuthenticateUser();
        List<Product> products = productRepository.getAllFromUserCompareProductList(user.getId(), pageable);
        List<ProductCompareResponse> productCompareResponses = new ArrayList<>();
        for (Product product : products) {
            ProductCompareResponse productCompareResponse
                    = new ProductCompareResponse(product.getId(), product.getProductName(), product.getProductImage(), product.getSubproducts().get(0).getCharacteristics(),
                    product.getBrand().getBrandName(), product.getColor(), product.getProductPrice());
            productCompareResponses.add(productCompareResponse);
        }
        return productCompareResponses;
    }

    public Map<String, Integer> countOfCompareList() {
        User user = getAuthenticateUser();
        Map<String, Integer> compares = new HashMap<>();
        LinkedList<Integer> counts = productRepository.countOfProductInCompare(user.getId());
        LinkedList<String> names = productRepository.categoryNameInCompare(user.getId());
        for (int i = 0; i < names.size(); i++) {
            compares.put(names.get(i), counts.get(i));
        }
        return compares;
    }

    public SimpleResponse cleanCompareProducts() {
        User user = getAuthenticateUser();
        user.getCompareProductsList().clear();
        userRepository.save(user);
        return new SimpleResponse("User compare products successfully", "ok");
    }

    @Override
    public SimpleResponse cleanFavoriteProducts() {
        User user = getAuthenticateUser();
        user.getFavoritesList().clear();
        userRepository.save(user);
        return new SimpleResponse("User's favorite list successfully cleaned", "ok");
    }
}
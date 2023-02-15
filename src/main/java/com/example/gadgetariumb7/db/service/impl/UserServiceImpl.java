package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.Subproduct;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.repository.SubproductRepository;
import com.example.gadgetariumb7.db.repository.UserRepository;
import com.example.gadgetariumb7.db.service.UserService;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.dto.response.SubproductCardResponse;
import com.example.gadgetariumb7.exceptions.BadRequestException;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SubproductRepository subproductRepository;
    private final ProductRepository productRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public SimpleResponse addToBasketList(int orderCount, Long subProductId) {
        User user = getAuthenticateUser();
        Subproduct subproduct = subproductRepository.findById(subProductId).orElseThrow(() -> new NotFoundException("Subproduct not found"));

        if (!user.getBasketList().containsKey(subproduct)) {
            if (orderCount <= subproduct.getCountOfSubproduct()) {
                user.getBasketList().put(subproduct, orderCount);
            } else {
                throw new BadRequestException(String.format("The orderCount(%d) larger > then subProductCount(%d)", orderCount, subproduct.getCountOfSubproduct()));
            }
        } else {
            throw new BadRequestException(String.format("Subproduct with id %d already exist in basket", subProductId));
        }

        subproductRepository.save(subproduct);
        userRepository.save(user);

        return new SimpleResponse("Subproduct successfully add to basket list", "ok");
    }

    @Override
    public SimpleResponse deleteFromBasketList(List<Long> productsId) {
        User user = getAuthenticateUser();

        for (Long id : productsId) {
            Subproduct subproduct = subproductRepository.findById(id).orElseThrow(() -> new NotFoundException("Subproduct not found"));
            if (user.getBasketList().containsKey(subproduct)) {
                user.getBasketList().remove(subproduct);
            } else {
                throw new BadRequestException(String.format("Subproduct with id %d is not exist in basket", id));
            }
        }

        userRepository.save(user);

        return new SimpleResponse("Subproduct successfully deleted from basketList", "ok");
    }

    @Override
    public SimpleResponse moveToFavoriteList(List<Long> subProductsId) {
        User user = getAuthenticateUser();
        subProductsId.forEach(x -> {
            Subproduct subproduct = subproductRepository.findById(x).orElseThrow(() -> new NotFoundException("Subproduct not found"));
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
        return new SimpleResponse("Subproduct successfully moved to favorite list", "ok");
    }

    @Override
    public List<SubproductCardResponse> getAllFromBasketList() {
        User user = getAuthenticateUser();
        List<Long> subproductsId = subproductRepository.getAllFromUserBasketList(user.getId());
        List<SubproductCardResponse> responses = new ArrayList<>();
        if (subproductsId.size() != 0){
            subproductsId.forEach(id -> {
                Subproduct s = subproductRepository.findById(id).get();
                SubproductCardResponse subproductCardResponse = new SubproductCardResponse(s.getId(), s.getImages().get(0), s.getCharacteristics(), s.getColor(), s.getProduct().getProductRating(), productRepository.getAmountOfFeedback(s.getProduct().getId()), s.getCountOfSubproduct(), s.getProduct().getProductVendorCode(), user.getBasketList().get(s), s.getPrice());
                if (s.getProduct().getDiscount() != null){
                    subproductCardResponse.setAmountOfDiscount(s.getProduct().getDiscount().getAmountOfDiscount());
                }
                responses.add(subproductCardResponse);
            });
        } else{
            throw new NotFoundException("Users basketList is empty");
        }
        return responses;
    }
}

package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.repository.UserRepository;
import com.example.gadgetariumb7.db.service.UserService;
import com.example.gadgetariumb7.dto.response.ProductResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
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
    private final ProductRepository productRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public SimpleResponse addAndRemoveToFavorites(Long productId) {
        User user = getAuthenticateUser();
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));

        if (user.getFavoritesList() == null) {
            user.setFavoritesList(Arrays.asList(product));
        } else {
            if (user.getFavoritesList().contains(product)) {
                user.getFavoritesList().remove(product);
                userRepository.save(user);
                return new SimpleResponse("Product successfully deleted from User's favorites", "ok");
            } else user.getFavoritesList().add(product);
        }
        userRepository.save(user);
        return new SimpleResponse("Product successfully added to User's favorites", "ok");
    }

    @Override
    public List<ProductResponse> getAllFavorites() {
        User user = getAuthenticateUser();
        List<ProductResponse> favorites = new ArrayList<>();
        for (Long productId : userRepository.getAllFavoritesByUserId(user.getId())) {
            favorites.add(productRepository.convertToResponse(productId));
        }
        return favorites;

    }
    }


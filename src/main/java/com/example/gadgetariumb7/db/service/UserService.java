package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

import java.util.List;


public interface UserService {

    SimpleResponse addAndRemoveToFavorites(Long productId);

    List<ProductCardResponse> getAllFavorites();

}

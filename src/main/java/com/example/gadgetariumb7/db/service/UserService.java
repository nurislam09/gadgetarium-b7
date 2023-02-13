package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.ProductResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

import java.util.List;


public interface UserService {

    SimpleResponse addAndRemoveToFavorites(Long productId);

    public List<ProductCardResponse> getAllFavorites();

}

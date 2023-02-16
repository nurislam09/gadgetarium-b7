package com.example.gadgetariumb7.db.service;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.SubproductCardResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

import java.util.List;


public interface UserService {

    SimpleResponse addAndRemoveToFavorites(Long productId);

    List<ProductCardResponse> getAllFavorites();

    SimpleResponse addToBasketList(int orderCount, Long subProductId);

    SimpleResponse deleteFromBasketList(List<Long> subProductsId);

    SimpleResponse moveToFavoriteList(List<Long> subProductsId);

    List<SubproductCardResponse> getAllFromBasketList();

    SimpleResponse addReview(ReviewRequest request);

}

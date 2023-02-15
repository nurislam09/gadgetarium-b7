package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.dto.response.SubproductCardResponse;

import java.util.List;

public interface UserService {

    SimpleResponse addToBasketList(int orderCount, Long subProductId);

    SimpleResponse deleteFromBasketList(List<Long> subProductsId);

    SimpleResponse moveToFavoriteList(List<Long> subProductsId);

    List<SubproductCardResponse> getAllFromBasketList();
}

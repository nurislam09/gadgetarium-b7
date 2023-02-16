package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.UserService;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.dto.response.SubproductCardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "User api")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Add or remove product from favoriteList", description = "This method for add or remove product from User's favoriteList")
    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public SimpleResponse addAndRemoveToFavoriteList(@RequestParam Long productId) {
        return userService.addAndRemoveToFavorites(productId);
    }

    @GetMapping()
    @Operation(summary = "Get all user's favorite products", description = "This endpoint return user's all favorite products")
    @PreAuthorize("isAuthenticated()")
    public List<ProductCardResponse> getAllFavorites() {
        return userService.getAllFavorites();
    }

    @Operation(summary = "Add product to basket list", description = "This endpoint adds a product to a user's basket list")
    @PostMapping("/addToBasketList")
    @PreAuthorize("isAuthenticated()")
    public SimpleResponse addToBasketList(@RequestParam int orderCount,
                                          @RequestParam Long productId) {
        return userService.addToBasketList(orderCount, productId);
    }

    @Operation(summary = "Delete a product from basket list", description = "This endpoint delete a product to a user's basket list")
    @DeleteMapping()
    @PreAuthorize("isAuthenticated()")
    public SimpleResponse delete(@RequestBody List<Long> productsId) {
        return userService.deleteFromBasketList(productsId);
    }

    @Operation(summary = "Move a subproduct from basket list to favorite list", description = "This endpoint move subproducts from user's basket list to favorite list")
    @PostMapping("/moveToFavoriteList")
    @PreAuthorize("isAuthenticated()")
    public SimpleResponse moveToFavoriteList(@RequestBody List<Long> productsId) {
        return userService.moveToFavoriteList(productsId);
    }

    @GetMapping("/getAllFromBasketList")
    @Operation(summary = "Get all products from users basket list", description = "This endpoint return products from users basket list")
    @PreAuthorize("isAuthenticated()")
    public List<SubproductCardResponse> getAllFromBasketList() {
        return userService.getAllFromBasketList();
    }

    @Operation(summary = "This method for save review", description = "This endpoint save review with array of images")
    @PostMapping()
    @PreAuthorize("hasAuthority('Customer')")
    public SimpleResponse save(@RequestBody ReviewRequest reviewRequest) {
        return userService.addReview(reviewRequest);
    }
}

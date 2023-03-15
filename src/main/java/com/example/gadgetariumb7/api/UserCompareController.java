package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.UserService;
import com.example.gadgetariumb7.dto.response.ProductCompareResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userCompare")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "User compare API")
@PreAuthorize("isAuthenticated()")
public class UserCompareController {
    private final UserService userService;

    @Operation(summary = "Add or remove product from compareList", description = "This method for add or remove product from User's compareList")
    @PostMapping
    public SimpleResponse addAndRemoveToFavoriteList(@RequestParam Long productId) {
        return userService.addAndRemoveToFavorites(productId);
    }

    @Operation(summary = "Get all products from users compare list", description = "This endpoint return products from users compare list")
    @GetMapping
    public List<ProductCompareResponse> getAllFromCompareList(@RequestParam String categoryName, @RequestParam int size, int page) {
        return userService.getAllFromUserCompareProductList(categoryName, size, page);
    }
    @Operation(summary = "Clean compareTo list", description = "This endpoint clean all compare list where user id is equal")
    @DeleteMapping("/{id}")
    public void cleanCompare(@PathVariable Long id) {
        userService.cleanCompareTO(id);
    }

    @Operation(summary = "Get count of all products in compare list", description = "This endpoint get count all product in compare list")
    @GetMapping("/{id}")
    public Map<String, Integer> countOfCompareTO(@PathVariable Long id) {
        return userService.countOfCompareList(id);
    }
}
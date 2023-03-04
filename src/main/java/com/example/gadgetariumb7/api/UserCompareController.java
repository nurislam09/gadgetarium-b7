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
    public List<ProductCompareResponse> getAllFromCompareList(@RequestParam String categoryName, @RequestParam int size) {
        return userService.getAllFromUserCompareProductList(categoryName, size);
    }
}
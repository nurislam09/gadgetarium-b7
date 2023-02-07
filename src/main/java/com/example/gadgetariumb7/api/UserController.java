package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.UserService;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}

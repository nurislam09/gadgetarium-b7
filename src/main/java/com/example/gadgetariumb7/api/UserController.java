package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.UserService;
import com.example.gadgetariumb7.dto.request.ReviewRequest;
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

    @Operation(summary = "This method for save review", description = "This endpoint save review with array of images")
    @PostMapping()
    @PreAuthorize("hasAuthority('Customer')")
    public SimpleResponse save(@RequestBody ReviewRequest reviewRequest) {
        return userService.addReview(reviewRequest);
    }
}

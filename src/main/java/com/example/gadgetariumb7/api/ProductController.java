package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Product api")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    @Operation(summary = "Get all products to main page", description = "This endpoint return AllProductResponse which contains three different response array")
    public AllProductResponse getAllProductMainPage() {
        return productService.getAllProductToMP();
    }

    @GetMapping("/catalog")
    @Operation(summary = "get products in catalog", description = "This endpoint return products in catalog depending on parameters which give the user in filter")
    public List<ProductCardResponse> getCatalog(@RequestParam(value = "categoryName") String categoryName, @RequestParam(value = "subCategoryName", required = false) String subCategoryName,
                                                @RequestParam(value = "minPrice", required = false) Integer minPrice, @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                                @RequestParam(value = "colors", required = false) List<String> colors, @RequestParam(value = "memory", required = false) Integer memory,
                                                @RequestParam(value = "ram", required = false) Byte ram) {

        return productService.filterByParameters(categoryName, subCategoryName, minPrice, maxPrice, colors, memory, ram);
    }
}

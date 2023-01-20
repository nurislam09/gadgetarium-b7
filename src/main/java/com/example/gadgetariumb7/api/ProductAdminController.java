package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.service.ProductServiceImpl;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductAdminController {
    private final ProductServiceImpl productService;

    @GetMapping("/getAllProducts")
    private List<ProductAdminResponse> getAllProduct(
            @RequestParam(value = "productType") String productType,
            @RequestParam(value = "fieldToSort", required = false) String fieldToSort,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        return productService.productAdminResponses(productType, fieldToSort, startDate, endDate);
    }

    @DeleteMapping("/delete/{id}")
    private void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }

    @PutMapping("/update")
    private void update(
            @RequestParam(value = "ID") Long id,
            @RequestParam(value = "Артикул", required = false) Integer vendorCode,
            @RequestParam(value = "Наименования товара", required = false) Integer productCount,
            @RequestParam(value = "Цена товара", required = false) Integer productPrice){
        productService.update(id, vendorCode, productCount, productPrice);
    }

    @GetMapping("/search")
    private List<ProductAdminResponse> search(@RequestParam(value = "text") String text){
        return productService.search(text);
    }
}

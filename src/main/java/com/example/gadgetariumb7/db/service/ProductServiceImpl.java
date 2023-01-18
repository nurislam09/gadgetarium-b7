package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Discount;
import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {
    private final ProductRepository productRepository;

    public List<ProductAdminResponse> productAdminResponses() {
        List<ProductAdminResponse> productAdminResponses = productRepository.getAllProductsAdmin();
        productAdminResponses.forEach(x -> x.setProductImages(productRepository.getById(x.getId()).getProductImages().get(0)));
        return productAdminResponses;
    }

    @PostConstruct
    public void initProduct() {
//        Discount discount = new Discount();
//        discount.setAmountOfDiscount((byte) 50);
//        discount.setDiscountStartDate(LocalDate.now());
//        discount.setDiscountEndDate(LocalDate.of(2023, 9, 12));

        Product product = new Product("Iphone", 9000, 12, 1234, 12, Arrays.asList("image1", "image2", "image3"));
        Product product1 = new Product("Laptop", 8000, 13, 4321, 12, Arrays.asList("image4", "image5", "image6"));
        Product product2 = new Product("Iphone", 3000, 11, 987, 12, Arrays.asList("image7", "image8", "image9"));


//        product.setDiscount(discount);
//        product1.setDiscount(discount);
//        product2.setDiscount(discount);

        productRepository.save(product);
        productRepository.save(product1);
        productRepository.save(product2);
    }
}

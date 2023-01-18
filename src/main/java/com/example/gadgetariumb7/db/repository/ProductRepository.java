package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select new com.example.gadgetariumb7.dto.response.ProductAdminResponse" +
            "(id," +
            "productVendorCode," +
            "productName," +
            "productCount," +
            "subproducts.size," +
            "productPrice" +
//            "(p.productPrice * ((100 - p.discount.amountOfDiscount) / 100))," +
//            "p.discount.amountOfDiscount" +
            ") from Product")
    List<ProductAdminResponse> getAllProductsAdmin();
}
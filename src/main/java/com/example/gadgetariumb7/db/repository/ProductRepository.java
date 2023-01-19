package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
//            "(p.id," +
//            "p.productName, " +
//            "p.productCount," +
//            "p.productPrice," +
//            "p.productPrice * ((100 - p.discount.amountOfDiscount) / 100)," +
//            "p.productStatus," +
//            "p.productRating)" +
//            " from Product p where p.c ")
//    List<Product> getAllNewProduct();


    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
            "(p.id," +
            "p.productName, " +
            "p.productCount," +
            "p.productPrice," +
            "p.productPrice * ((100 - p.discount.amountOfDiscount) / 100)," +
            "p.productStatus," +
            "p.productRating)" +
            " from Product p where p.discount is not null ")
    List<ProductCardResponse> getAllDiscountProduct();

    List<Product> getAllRecomindationProduct();
}

package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
            "createAt," +
            "productPrice" +
            ") from Product")
    List<ProductAdminResponse> getAllProductsAdmin(Pageable pageable);


    @Query("select new com.example.gadgetariumb7.dto.response.ProductAdminResponse" +
            "(p.id," +
            "p.productVendorCode," +
            "p.productName," +
            "p.productCount," +
            "p.subproducts.size," +
            "p.createAt," +
            "p.productPrice" +
            ") from Product p where " +
            "cast(p.productVendorCode as string) like concat(:text, '%') or " +
            "upper(p.productName) like concat('%',:text,'%')")
    List<ProductAdminResponse> search(@Param("text") String text, Pageable pageable);
}
package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
            "(p.id," +
            "p.productName, " +
            "p.productCount," +
            "p.productPrice," +
            "p.productStatus," +
            "p.productRating)" +
            "from Product p where p.productStatus = 0 order by p.createAt")
    List<ProductCardResponse> getAllNewProduct();


    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
            "(p.id," +
            "p.productName, " +
            "p.productCount," +
            "p.productPrice," +
            "p.productStatus," +
            "p.productRating)" +
            "from Product p where p.discount is not null")
    List<ProductCardResponse> getAllDiscountProduct();

    @Query(nativeQuery = true, value = "select image_url from product_images where id = :id limit 1")
    String getFirstImage(Long id);

    @Query("select (p.productPrice -((p.productPrice * p.discount.amountOfDiscount) /100)) from Product p  where p.id = :id ")
    int getDiscountPrice(Long id);

    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
            "(p.id," +
            "p.productName, " +
            "p.productCount," +
            "p.productPrice," +
            "p.productStatus," +
            "p.productRating)" +
            " from Product p where p.productStatus = 1")
    List<ProductCardResponse> getAllRecommendationProduct();

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
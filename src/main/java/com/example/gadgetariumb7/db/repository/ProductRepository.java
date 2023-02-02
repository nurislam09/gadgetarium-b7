package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
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
    @Query("select count (r.product) from Review r where r.product.id =:id")
    int getAmountOfFeedback(Long id);

    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
            "(p.id," +
            "p.productName, " +
            "p.productCount," +
            "p.productPrice," +
            "p.productStatus," +
            "p.productRating)" +
            " from Product p where p.productStatus = 1")
    List<ProductCardResponse> getAllRecommendationProduct();
    @Query(nativeQuery = true , value = "select sum (o.count_of_product) from orders o where o.order_status = 0 or 1 or 2")
    int getSoldProducts();
}

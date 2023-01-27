package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
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

    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
            "(p.id," +
            "p.productName," +
            "p.productCount," +
            "p.productPrice," +
            "p.productStatus," +
            "p.productRating)" +
            "from Product p " +
            "where upper(p.category.categoryName) like upper(:categoryName) and " +
            "upper(p.subCategory.subCategoryName) like upper(:subCategoryName) or " +
            "p.productPrice between :minPrice and :maxPrice or " +
            "p.color IN (:colors) or " +
            "p.memoryOfPhone = :memory or " +
            "p.memoryOfSmartWatch = :memory or " +
            "p.memoryOfTablet =: memory or ")
//    @Query(" FROM Product c WHERE (:category is null or c.category = :category) AND (:minPrice is null or c.price >= :minPrice) AND (:maxPrice is null or c.price <= :maxPrice) AND (:colors is null or c.color IN :colors) AND (:memory is null or c.memory = :memory) AND (:ram is null or c.ram = :ram)")
    List<Product> filterByParameters(String categoryName, String subCategoryName, int minPrice, int maxPrice, List<Color> colors, int memory, Byte ram);
}

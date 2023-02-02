package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    //Тебе нужно сделать так чтобы в зависимости от выбранной категории то и ram и memory были соответсвующими этому продукту
    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
            "(p.id," +
            "p.productName," +
            "p.productCount," +
            "p.productPrice," +
            "p.productStatus," +
            "p.productRating" +
            ") " +
            "from Product p " +
            "where upper(p.category.categoryName) like :categoryName AND " +
            "(upper(p.subCategory.subCategoryName) is null or upper(p.subCategory.subCategoryName) like %:subCategoryName%) AND " +
            "(:minPrice is null or p.productPrice between :minPrice and :maxPrice) AND " +
            "(:colors is null or p.color IN (:colors)) AND " +
            "(:categoryName = 'СМАРТФОНЫ' and p.memoryOfPhone >= :memory or " +
            ":categoryName = 'ПЛАНШЕТЫ' and p.memoryOfTablet >= :memory or " +
            ":categoryName = 'НОУТБУКИ' and p.videoCardMemory >= :memory or " +
            ":categoryName = 'CМАРТ-ЧАСЫ И БРАСЛЕТЫ' and p.memoryOfSmartWatch >= :memory) AND " +
            "(:categoryName = 'СМАРТФОНЫ' and p.ramOfPhone >= :ram or " +
            ":categoryName = 'ПЛАНШЕТЫ' and p.ramOfTablet >= :ram or " +
            ":categoryName = 'НОУТБУКИ' and p.ramOfLaptop >= :ram) ")
    List<ProductCardResponse> filterByParameters(@Param(value = "categoryName") String categoryName,
                                                 @Param(value = "subCategoryName") String subCategoryName,
                                                 @Param(value = "minPrice") Integer minPrice, @Param(value = "maxPrice") Integer maxPrice,
                                                 @Param(value = "colors") List<String> colors, @Param(value = "memory") Integer memory,
                                                 @Param(value = "ram") Byte ram);
}

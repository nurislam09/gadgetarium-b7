package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import com.example.gadgetariumb7.dto.response.ProductSearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
            "(p.id," +
            "p.productImage," +
            "p.productName, " +
            "p.productCount," +
            "p.productPrice," +
            "p.productStatus," +
            "p.productRating)" +
            "from Product p where p.productStatus = 0 order by p.createAt")
    List<ProductCardResponse> getAllNewProduct(Pageable pageable);

    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
            "(p.id," +
            "p.productImage," +
            "p.productName, " +
            "p.productCount," +
            "p.productPrice," +
            "p.productStatus," +
            "p.productRating)" +
            "from Product p where p.discount is not null")
    List<ProductCardResponse> getAllDiscountProduct(Pageable pageable);

    @Query("select (p.productPrice -((p.productPrice * p.discount.amountOfDiscount) /100)) from Product p  where p.id = :id ")
    int getDiscountPrice(Long id);

    @Query("select count (r.product) from Review r where r.product.id =:id")
    int getAmountOfFeedback(Long id);

    @Query("select new com.example.gadgetariumb7.dto.response.ProductCardResponse " +
            "(p.id," +
            "p.productImage," +
            "p.productName, " +
            "p.productCount," +
            "p.productPrice," +
            "p.productStatus," +
            "p.productRating)" +
            " from Product p where p.productStatus = 1")
    List<ProductCardResponse> getAllRecommendationProduct(Pageable pageable);

    @Query("select new com.example.gadgetariumb7.dto.response.ProductAdminResponse" +
            "(id," +
            "productImage," +
            "productVendorCode," +
            "productName," +
            "productCount," +
            "subproducts.size," +
            "createAt," +
            "productPrice," +
            "productStatus" +
            ") from Product")
    List<ProductAdminResponse> getAllProductsAdmin(Pageable pageable);

    @Query("select new com.example.gadgetariumb7.dto.response.ProductAdminResponse" +
            "(id," +
            "productImage," +
            "productVendorCode," +
            "productName," +
            "productCount," +
            "subproducts.size," +
            "createAt," +
            "productPrice," +
            "productStatus" +
            ") from Product")
    List<ProductAdminResponse> getAllProductsAdminWithoutPagination();

    @Query("select new com.example.gadgetariumb7.dto.response.ProductAdminResponse" +
            "(p.id," +
            "p.productImage," +
            "p.productVendorCode," +
            "p.productName," +
            "p.productCount," +
            "p.subproducts.size," +
            "p.createAt," +
            "p.productPrice," +
            "p.productStatus" +
            ") from Product p where " +
            "cast(p.productVendorCode as string) like upper(concat(:text, '%')) or " +
            "upper(p.productName) like upper(concat('%',:text,'%'))")
    List<ProductAdminResponse> search(@Param("text") String text, Pageable pageable);

    @Query("select count(p.id) from Product p where " +
            "cast(p.productVendorCode as string) like upper(concat(:text, '%')) or " +
            "upper(p.productName) like upper(concat('%',:text,'%'))")
    int searchCount(@Param("text") String text);

    @Query("select p.discount.amountOfDiscount from Product p where p.id = :id")
    int getAmountOfDiscount(Long id);

    @Query("select new com.example.gadgetariumb7.dto.response.ProductSearchResponse" +
            "(p.id," +
            "p.productName," +
            "p.productCount," +
            "p.productPrice," +
            "p.productStatus," +
            "p.productRating," +
            "p.brand.brandName," +
            "p.category.categoryName," +
            "p.subCategory.subCategoryName," +
            "p.discount.amountOfDiscount," +
            "p.description," +
            "p.productVendorCode," +
            "p.color" +
            ") from Product p where " +
            "upper(p.productName) like upper(concat('%',:text,'%')) OR " +
            "cast(p.productCount as string) like :text OR " +
            "cast(p.productPrice as string) like concat(:text,'%') OR " +
            "cast(p.productStatus as string) like upper(:text) OR " +
            "cast(p.productRating as string) like :text OR " +
            "upper(p.category.categoryName) like upper(concat ('%',:text,'%')) OR " +
            "upper(p.brand.brandName) like upper(concat('%',:text, '%')) OR " +
            "upper(p.subCategory.subCategoryName) like upper(concat('%', :text, '%')) OR " +
            "cast(concat(p.discount.amountOfDiscount,'%') as string) like :text OR " +
            "upper(p.description) like upper(concat('%',:text,'%')) OR " +
            "cast(p.productVendorCode as string) like upper(concat(:text, '%')) OR " +
            "upper(p.color) like upper(concat('%',:text,'%')) ")
    List<ProductSearchResponse> searchCatalog(@Param("text") String text, Pageable pageable);

    @Query("")
    List<String> getProductImage();
}
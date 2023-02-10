package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.dto.response.ProductCardResponse;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
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

    @Query(nativeQuery = true,value = "select sum(o.count_of_product) from orders o where o.order_status like 'DELIVERED'")
    int getCountSoldProducts();

    @Query(nativeQuery = true, value = "select sum(o.total_sum) from orders o where o.order_status like 'DELIVERED'")
    Long getSoldProductPrice();

    @Query(nativeQuery = true, value = "select sum(o.count_of_product) from orders o where o.order_status in ('WAITING', 'ORDER_READY', 'ON_THE_WAY')")
    int getCountOrderProduct();

    @Query(nativeQuery = true, value = "select sum(o.total_sum) from orders o where o.order_status in ('WAITING', 'ORDER_READY', 'ON_THE_WAY')")
    Long getOrderProductPrice();

    @Query(nativeQuery = true, value = "select sum(o.total_sum) from orders o where o.order_status like 'DELIVERED' and o.date_of_order between date(now()) and date(now()) + interval '1' day")
    Long getCurrentPeriodPerDay();

    @Query(nativeQuery = true, value = "select sum(o.total_sum) from orders o where o.order_status like 'DELIVERED' and o.date_of_order between date_trunc('month', now()) and date_trunc('month', now()) + interval '1' MONTH - interval '1' second")
    Long getCurrentPeriodPerMonth();

    @Query(nativeQuery = true, value = "select sum(o.total_sum) from orders o where o.order_status like 'DELIVERED' and o.date_of_order between date_trunc('year', now()) and date_trunc('year', now()) + interval '1' year - interval '1' second")
    Long getCurrentPeriodPerYear();

    @Query(nativeQuery = true, value = "select sum(o.total_sum) from orders o where o.order_status like 'DELIVERED' and o.date_of_order between date(NOW()) - interval '1' day and date(now()) - interval '1' second")
    Long getPreviousPeriodPerDay();

    @Query(nativeQuery = true, value = "select sum(o.total_sum) from orders o where o.order_status like 'DELIVERED' and o.date_of_order between date_trunc('month', now() - interval '1' month) and date_trunc('month', now()) - interval '1' second")
    Long getPreviousPeriodPerMonth();

    @Query(nativeQuery = true, value = "select sum(o.total_sum) from orders o where o.order_status like 'DELIVERED' and o.date_of_order between date_trunc('year', now() - INTERVAL '1' year) and date_trunc('year', now()) - interval '1' second")
    Long getPreviousPeriodPerYear();

    @Query("select new com.example.gadgetariumb7.dto.response.ProductAdminResponse" +
            "(id," +
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
            "(p.id," +
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

    @Query("select p.discount.amountOfDiscount from Product p where p.id = :id")
    int getAmountOfDiscount(Long id);

}
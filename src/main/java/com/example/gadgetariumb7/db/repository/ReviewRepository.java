package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Review;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.dto.response.ReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select count(productGrade) from Review where productGrade = :productGrade ")
    int countReviewByProductGrade(byte productGrade);

    @Query(nativeQuery = true, value = "select image_url from product_images where id = :id limit 1")
    String getFirstImage(Long id);

    @Query("select r.user from Review r where r.id = :id")
    User getUserReview(Long id);

    @Query("select new com.example.gadgetariumb7.dto.response.ReviewResponse(" +
            "r.id," +
            "r.statusOfResponse," +
            "r.responseOfReview," +
            "r.productGrade," +
            "r.product.productVendorCode," +
            "r.product.productName) from Review r")
    List<ReviewResponse> getAllByAdmin();

}

package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Review;
import com.example.gadgetariumb7.dto.response.ReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("select count(productGrade) from Review group by productGrade order by productGrade")
    void sortByGrade(byte productGrade);

    @Query("select r from Review r")
    List<ReviewResponse> getAllReviews();


    @Query("select new com.example.gadgetariumb7.dto.response.ReviewResponse(o.id," +
            " o.product.productName," +
            "o.responseOfReview," +
            "o.product.productImages, "+
            "o.productGrade," +
            "o.product.productVendorCode) from Review o")
    List<ReviewResponse> findAllReviewsByStatus();
}

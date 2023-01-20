package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("select count(productGrade) from Review group by productGrade order by productGrade")
    void sortByGrade(byte productGrade);
}

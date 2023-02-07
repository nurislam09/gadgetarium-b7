package com.example.gadgetariumb7.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_gen")
    @SequenceGenerator(name = "review_gen", sequenceName = "review_seq", allocationSize = 1)
    private Long id;

    private LocalDateTime reviewTime;
    private byte productGrade;

    private String userReview;
    private String responseOfReview;
    private boolean statusOfResponse;
    private String image;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User user;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Product product;

    public Review(Long id, byte productGrade, String responseOfReview, boolean statusOfResponse, String image) {
        this.id = id;
        this.productGrade = productGrade;
        this.responseOfReview = responseOfReview;
        this.statusOfResponse = statusOfResponse;
        this.image = image;
    }

}

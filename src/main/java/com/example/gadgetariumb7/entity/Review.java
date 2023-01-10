package com.example.gadgetariumb7.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @SequenceGenerator(name = "review_seq", sequenceName = "review_seq", allocationSize = 1)
    private Long id;

    private LocalDateTime reviewTime;

    private byte productGrade;

    private String responseOfReview;

    private boolean statusOfResponse;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH})
    private User user;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH})
    private Product product;
}

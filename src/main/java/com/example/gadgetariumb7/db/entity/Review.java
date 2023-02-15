package com.example.gadgetariumb7.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private double productGrade;

    private String userReview;
    private String responseOfReview;
    private boolean statusOfResponse;

    @ElementCollection
    @CollectionTable(name = "review_images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "image_url", length = 10000)
    private List<String> images;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User user;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Product product;


}

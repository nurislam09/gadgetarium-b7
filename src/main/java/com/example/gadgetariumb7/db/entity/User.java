package com.example.gadgetariumb7.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String image;
    private String address;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH})
    private List<Product> basketList;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH})
    private List<Product> favoritesList;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE}, mappedBy = "user")
    private List<Review> userReviews;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH})
    private List<Product> compareProductsList;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH})
    private List<Product> orderHistoryList;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH})
    private List<Product> viewedProductsList;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH}, mappedBy = "user")
    private List<Order> orders;

    @ManyToOne(cascade = {PERSIST, REFRESH, MERGE, DETACH})
    @JoinColumn(name = "role_id")
    private Role role;
}

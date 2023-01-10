package com.example.gadgetariumb7.entity;


import com.example.gadgetariumb7.entity.enums.Gender;
import com.example.gadgetariumb7.entity.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    private String productName;

    private int productPrice;

    private int orderCount;

    private int productVendorCode;

    private int productCount;

    private Byte guarantee;

    private String videoReview;

    private String PDF;

    private String description;

    private ProductStatus productStatus;

    private Byte productRating;

    private Color color;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Discount discount;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private Brand brand;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private Category category;


    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private SubCategory subCategory;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "image_url")
    List<String> productImages;

    @OneToMany(cascade = {ALL}, mappedBy = "product")
    List<Review> usersReviews;

    @OneToMany(cascade = {ALL}, mappedBy = "product")
    List<SubProduct> subproducts;

    @ManyToMany(cascade = {MERGE, REFRESH, DETACH})
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;

    //PHONE
    private String simCard;
    private int memoryOfPhone;
    private byte ramOfPhone;

    //TABLET
    private String screenResolutionTablet;
    private double screenSizeTablet;
    private int memoryOfTablet;
    private byte ramOfTablet;
    private double ScreenDiagonal;
    private int batteryPowerty;

    //LAPTOP
    private String laptopCPU;
    private String screenResolutionLaptop;
    private String appointmentOfLaptop;
    private Byte ramOfLaptop;
    private byte videoCardMemory;
    private double screenSizeLaptop;

    //SMARTWATCH
    private String wirelessInterface;
    private int memory;
    private String caseShape;
    private String braceletMaterial;
    private String watchMaterial;
    private Gender gender;
    private String waterProof;
    private double ScreenDisplay;

}

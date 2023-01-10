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
    private LocalDate createAt;
    private Color color;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = FetchType.EAGER)
    private Discount discount;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST}, fetch = FetchType.EAGER)
    private Brand brand;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST}, fetch = FetchType.EAGER)
    private Category category;


    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST}, fetch = FetchType.EAGER)
    private SubCategory subCategory;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "image_url")
    List<String> productImages;

    @OneToMany(cascade = {ALL}, fetch = FetchType.EAGER, mappedBy = "product")
    List<Review> usersReviews;

    @OneToMany(cascade = {ALL}, fetch = FetchType.EAGER, mappedBy = "product")
    List<SubProduct> subproducts;

    @ManyToMany(cascade = {MERGE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Order> orders;

    //PHONE
    private String simCard;
    private int memoryOfPhone;
    private byte ramOfPhone;

    //TABLET
    private String screenResolutionTablet; //(разрешение экрана)
    private double screenSizeTablet; //(размер экрана)
    private int memoryOfTablet; //(об.памяти)
    private byte ramOfTablet; // (оп.память)
    private double ScreenDiagonal; //(диоганаль экрана)
    private int batteryPowerty; //(емкость.аккум)

    //LAPTOP
    private String laptopCPU; //(процессор ноутбука)
    private String screenResolutionLaptop; //(разрешение экрана)
    private String appointmentOfLaptop; //(назначение)
    private Byte ramOfLaptop; // (оп.память)
    private byte videoCardMemory;
    private double screenSizeLaptop;

    //SMARTWATCH
    private String wirelessInterface;//(беспроводные интерфейсы)
    private int memory;
    private String caseShape;//(форма корпуса
    private String braceletMaterial;
    private String watchMaterial;// (м.часы)
    private Gender gender;
    private String waterProof;
    private double ScreenDisplay;

}

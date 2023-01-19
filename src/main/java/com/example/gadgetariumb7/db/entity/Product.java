package com.example.gadgetariumb7.db.entity;


import com.example.gadgetariumb7.db.enums.Gender;
import com.example.gadgetariumb7.db.enums.ProductStatus;

import javax.persistence.*;

import com.example.gadgetariumb7.dto.request.ProductRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    @SequenceGenerator(name = "product_gen", sequenceName = "product_seq", allocationSize = 1)
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

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private Discount discount;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE})
    private Brand brand;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE})
    private Category category;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE})
    private Subcategory subCategory;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "image_url")
    List<String> productImages;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "product")
    List<Review> usersReviews;

    @OneToMany(cascade = {ALL}, mappedBy = "product")
    List<Subproduct> subproducts;

    @ManyToMany(cascade = {MERGE, REFRESH, DETACH})
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;

    private String simCard;
    private int memoryOfPhone;
    private byte ramOfPhone;

    private String screenResolutionTablet;
    private double screenSizeTablet;
    private int memoryOfTablet;
    private byte ramOfTablet;
    private double screenDiagonal;
    private int batteryCapacity;

    private String laptopCPU;
    private String screenResolutionLaptop;
    private String appointmentOfLaptop;
    private Byte ramOfLaptop;
    private byte videoCardMemory;
    private double screenSizeLaptop;

    private String wirelessInterface;
    private int memoryOfSmartWatch;
    private String caseShape;
    private String braceletMaterial;
    private String watchMaterial;
    private Gender gender;
    private String waterProof;
    private double screenDisplay;
    private double sizeOfWatch;

    public Product(ProductRequest productRequest, Brand brand, Category category, Subcategory subCategory) {
        this.productName = productRequest.getProductName();
        this.productPrice = productRequest.getProductPrice();
        this.productVendorCode = productRequest.getProductVendorCode();
        this.productCount = productRequest.getProductCount();
        this.guarantee = productRequest.getGuarantee();
        this.videoReview = productRequest.getVideoReview();
        this.PDF = productRequest.getPDF();
        this.description = productRequest.getDescription();
        this.color = productRequest.getColor();
        this.brand = brand;
        this.category = category;
        this.subCategory = subCategory;
    }
    //    public Product(ProductRequest productRequest, String productName) {
//        this.productName = productRequest.getProductName();
//        this.category = productRequest.getCategory();
//        this.subCategory = productRequest.getSubCategory();
//        this.brand = productRequest.getBrand();
//        this.guarantee = productRequest.getGuarantee();
//        this.memoryOfPhone = productRequest.getMemoryOfPhone();
//        this.color = productRequest.getColor();
//        this.ramOfPhone = productRequest.getRamOfPhone();
//        this.simCard = productRequest.getSimCard();
//        this.productImages = new ArrayList<>(productRequest.getProductImages());
//    }

//    public Product(ProductRequest productRequest, Gender gender) {
//        this.productName = productRequest.getProductName();
//        this.category = productRequest.getCategory();
//        this.subCategory = productRequest.getSubCategory();
//        this.brand = productRequest.getBrand();
//        this.guarantee = productRequest.getGuarantee();
//        this.memoryOfSmartWatch = productRequest.getMemoryOfSmartWatch();
//        this.color = productRequest.getColor();
//        this.braceletMaterial = productRequest.getBraceletMaterial();
//        this.watchMaterial = productRequest.getWatchMaterial();
//        this.sizeOfWatch = productRequest.getSizeOfWatch();
//        this.screenDisplay = productRequest.getScreenDisplay();
//        this.gender = productRequest.getGender();
//        this.waterProof = productRequest.getWaterProof();
//        this.caseShape = productRequest.getCaseShape();
//        this.wirelessInterface = productRequest.getWirelessInterface();
//        this.productImages = new ArrayList<>(productRequest.getProductImages());
//    }

//    public Product(ProductRequest productRequest, Double screenDiagonal) {
//        this.productName = productRequest.getProductName();
//        this.category = productRequest.getCategory();
//        this.subCategory = productRequest.getSubCategory();
//        this.brand = productRequest.getBrand();
//        this.guarantee = productRequest.getGuarantee();
//        this.color = productRequest.getColor();
//        this.screenResolutionTablet = productRequest.getScreenResolutionTablet();
//        this.screenSizeTablet = productRequest.getScreenSizeTablet();
//        this.memoryOfTablet = productRequest.getMemoryOfTablet();
//        this.ramOfTablet = productRequest.getRamOfTablet();
//        this.screenDiagonal = productRequest.getScreenDiagonal();
//        this.batteryCapacity = productRequest.getBatteryCapacity();
//        this.productImages = new ArrayList<>(productRequest.getProductImages());
//    }

//    public Product(ProductRequest productRequest, byte videoCardMemory) {
//        this.productName = productRequest.getProductName();
//        this.category = productRequest.getCategory();
//        this.subCategory = productRequest.getSubCategory();
//        this.brand = productRequest.getBrand();
//        this.guarantee = productRequest.getGuarantee();
//        this.color = productRequest.getColor();
//        this.screenResolutionLaptop = productRequest.getScreenResolutionLaptop();
//        this.laptopCPU = productRequest.getLaptopCPU();
//        this.videoCardMemory = productRequest.getVideoCardMemory();
//        this.ramOfLaptop = productRequest.getRamOfLaptop();
//        this.screenSizeLaptop = productRequest.getScreenSizeLaptop();
//        this.appointmentOfLaptop = productRequest.getAppointmentOfLaptop();
//        this.productImages = new ArrayList<>(productRequest.getProductImages());
//    }
}




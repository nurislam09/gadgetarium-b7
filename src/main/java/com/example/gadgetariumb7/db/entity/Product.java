package com.example.gadgetariumb7.db.entity;

import com.example.gadgetariumb7.db.enums.Gender;
import com.example.gadgetariumb7.db.enums.ProductStatus;

import javax.persistence.*;

import com.example.gadgetariumb7.dto.request.ProductRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @SequenceGenerator(name = "product_gen", sequenceName = "product_seq", allocationSize = 1, initialValue = 12)
    private Long id;

    private String productName;

    private int productPrice;

    private int orderCount;

    private int productVendorCode;

    private int productCount;

    private byte guarantee;

    @Column(length = 10000)
    private String videoReview;

    @Column(length = 10000)
    private String PDF;

    @Column(length = 10000)
    private String description;

    private ProductStatus productStatus;

    private Byte productRating;

    private LocalDateTime createAt;

    private String color;

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
    @Column(name = "image_url", length = 10000)
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
    private double ScreenDiagonal;
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
    private double ScreenDisplay;
    private double sizeOfWatch;

    public Product(ProductRequest productRequest, int productPrice, Brand brand, Category category, Subcategory subCategory, String productName) {
        this.productName = productRequest.getProductName();
        this.productPrice = productPrice;
        this.productVendorCode = productRequest.getProductVendorCode();
        this.productCount = productRequest.getProductCount();
        this.guarantee = productRequest.getGuarantee();
        this.videoReview = productRequest.getVideoReview();
        this.PDF = productRequest.getPDF();
        this.description = productRequest.getDescription();
        this.productImages = productRequest.getProductImages();
        this.color = productRequest.getColor();
        this.productImages = productRequest.getProductImages();
        this.brand = brand;
        this.category = category;
        this.subCategory = subCategory;
        this.memoryOfPhone = productRequest.getMemoryOfPhone();
        this.ramOfPhone = productRequest.getRamOfPhone();
        this.simCard = productRequest.getSimCard();
    }

    public Product(ProductRequest productRequest, int productPrice, Brand brand, Category category, Subcategory subCategory, Gender gender) {
        this.productName = productRequest.getProductName();
        this.productPrice = productPrice;
        this.productVendorCode = productRequest.getProductVendorCode();
        this.productCount = productRequest.getProductCount();
        this.guarantee = productRequest.getGuarantee();
        this.videoReview = productRequest.getVideoReview();
        this.PDF = productRequest.getPDF();
        this.description = productRequest.getDescription();
        this.color = productRequest.getColor();
        this.productImages = productRequest.getProductImages();
        this.brand = brand;
        this.category = category;
        this.subCategory = subCategory;
        this.wirelessInterface = productRequest.getWirelessInterface();
        this.memoryOfSmartWatch = productRequest.getMemoryOfSmartWatch();
        this.caseShape = productRequest.getCaseShape();
        this.braceletMaterial = productRequest.getBraceletMaterial();
        this.watchMaterial = productRequest.getWatchMaterial();
        this.gender = productRequest.getGender();
        this.waterProof = productRequest.getWaterProof();
        this.ScreenDisplay = productRequest.getScreenDisplay();
        this.sizeOfWatch = productRequest.getSizeOfWatch();
    }

    public Product(ProductRequest productRequest, int productPrice, Brand brand, Category category, Subcategory subCategory, int batteryCapacity, Double screenDiagonal) {
        this.productName = productRequest.getProductName();
        this.productPrice = productPrice;
        this.productVendorCode = productRequest.getProductVendorCode();
        this.productCount = productRequest.getProductCount();
        this.guarantee = productRequest.getGuarantee();
        this.videoReview = productRequest.getVideoReview();
        this.PDF = productRequest.getPDF();
        this.description = productRequest.getDescription();
        this.color = productRequest.getColor();
        this.productImages = productRequest.getProductImages();
        this.brand = brand;
        this.category = category;
        this.subCategory = subCategory;
        this.screenResolutionTablet = productRequest.getScreenResolutionTablet();
        this.screenSizeTablet = productRequest.getScreenSizeTablet();
        this.memoryOfTablet = productRequest.getMemoryOfTablet();
        this.ramOfTablet = productRequest.getRamOfTablet();
        this.ScreenDiagonal = productRequest.getScreenDiagonal();
        this.batteryCapacity = productRequest.getBatteryCapacity();

    }

    public Product(ProductRequest productRequest, int productPrice, Brand brand, Category category, Subcategory subCategory, byte videoCardMemory) {
        this.productName = productRequest.getProductName();
        this.productPrice = productPrice;
        this.productVendorCode = productRequest.getProductVendorCode();
        this.productCount = productRequest.getProductCount();
        this.guarantee = productRequest.getGuarantee();
        this.videoReview = productRequest.getVideoReview();
        this.PDF = productRequest.getPDF();
        this.description = productRequest.getDescription();
        this.color = productRequest.getColor();
        this.productImages = productRequest.getProductImages();
        this.brand = brand;
        this.category = category;
        this.subCategory = subCategory;
        this.laptopCPU = productRequest.getLaptopCPU();
        this.screenResolutionLaptop = productRequest.getScreenResolutionLaptop();
        this.appointmentOfLaptop = productRequest.getAppointmentOfLaptop();
        this.ramOfLaptop = productRequest.getRamOfLaptop();
        this.videoCardMemory = productRequest.getVideoCardMemory();
        this.screenSizeLaptop = productRequest.getScreenSizeLaptop();
    }
}




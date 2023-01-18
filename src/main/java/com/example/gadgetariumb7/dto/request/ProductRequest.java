package com.example.gadgetariumb7.dto.request;

import com.example.gadgetariumb7.db.entity.*;
import com.example.gadgetariumb7.db.enums.Gender;
import com.example.gadgetariumb7.db.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest{
    private String productName;

    private int productPrice;

    private int productVendorCode;

    private int productCount;

    private Byte guarantee;

    private String videoReview;

    private String PDF;

    private String description;

    private Color color;

    private Brand brand;

    private Category category;

    private Subcategory subCategory;

    List<String> productImages;

    List<Subproduct> subproducts;

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
}

package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.entity.Subproduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSingleResponse {
    @JsonIgnore
    private Long id;
    private String productName;
    private String productImage;
    private int productCount;
    private Long productVendorCode;
    private String categoryName;
    private String subCategoryName;
    private int countOfReviews;
    private int productPrice;
    private Double productRating;
    private String color;
    private String videoReview;
    private List<Subproduct> subproducts;
    private int discountPrice;
    private boolean isFavorite;

    private Map<String, Object> attribute;

    public ProductSingleResponse(Long id, String productName, String productImage, int productCount, Long productVendorCode, String categoryName, String subCategoryName,
                                 int countOfReviews, int productPrice, Double productRating, String color, List<Subproduct> subproducts){
        this.id = id;
        this.productName = productName;
        this.productImage = productImage;
        this.productCount = productCount;
        this.productVendorCode = productVendorCode;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.countOfReviews = countOfReviews;
        this.productPrice = productPrice;
        this.productRating = productRating;
        this.color = color;
        this.subproducts = subproducts;
    }

    public void setAttribute(String name, Object value){
        if(attribute == null){
            attribute = new HashMap<>();
        }
        attribute.put(name, value);
    }

    public Map<String, Object> getAttribute(){
        return attribute;
    }

}

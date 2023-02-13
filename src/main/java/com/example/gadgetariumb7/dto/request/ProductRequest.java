package com.example.gadgetariumb7.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor.DATE_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "Product name should be not null")
    @NotBlank(message = "Product name should be not blank")
    private String productName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "Product vendor code should be not null")
    @NotBlank(message = "Product vendor code should be not blank")
    private Long productVendorCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "Product guarantee should be not null")
    @NotBlank(message = "Product guarantee should be not blank")
    private byte guarantee;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "Product video review should be not null")
    @NotBlank(message = "Product video review should be not blank")
    private String videoReview;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "Product pdf should be not null")
    @NotBlank(message = "Product pdf should be not blank")
    private String PDF;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "Product description should be not null")
    @NotBlank(message = "Product description should be not blank")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "Brand id should be not null")
    @NotBlank(message = "Brand id should be not blank")
    private Long brandId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "Category id should be not null")
    @NotBlank(message = "Category id should be not blank")
    private Long categoryId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "Subcategory id should be not null")
    @NotBlank(message = "Subcategory id should be not blank")
    private Long subCategoryId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull(message = "Subproducts should be not null")
    @NotBlank(message = "Subproducts should be not blank")
    private List<SubProductRequest> subProductRequests;
}

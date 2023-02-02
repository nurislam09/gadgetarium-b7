package com.example.gadgetariumb7.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import java.awt.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogRequest {
    private String categoryName;
    private String subCategoryName;
    private int minPrice;
    private int maxPrice;
    private List<Color> colors;
    private String memory;
    private String ram;
}

package com.example.gadgetariumb7.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogRequest {
    private String categoryName;
    private Double minPrice;
    private Double maxPrice;
    private List<String> colors;
    private String memory;
    private String ram;
}

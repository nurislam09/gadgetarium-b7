package com.example.gadgetariumb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubproductResponse {
    private Long id;
    private int price;
    private int countOfSubproduct;
    private String color;
    private Map<String, String> characteristics = new HashMap<>();
    List<String> images;

    public SubproductResponse(Long id, int countOfSubproduct, List<String> images, int price, String color, Map<String, String> characteristics) {
        this.id = id;
        this.price = price;
        this.countOfSubproduct = countOfSubproduct;
        this.color = color;
        this.characteristics = characteristics;
        this.images = images;
    }
}

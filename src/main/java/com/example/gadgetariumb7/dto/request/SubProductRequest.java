package com.example.gadgetariumb7.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubProductRequest {
    private int memory;
    private String laptopCPU;
    private String color;
    private List<String> images;
}
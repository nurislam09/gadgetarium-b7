package com.example.gadgetariumb7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class BrandDTO {
    @NotNull
    private String image;

    @Size(min = 2, max = 20)
    private String brandName;
}

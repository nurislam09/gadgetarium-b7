package com.example.gadgetariumb7.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InforgraphicsRequest {

    private Long soldPrice;
    private int soldCount;
    private Long orderPrice;
    private int orderCount;
    private Long currentPeriod;
    private Long previousPeriod;
}

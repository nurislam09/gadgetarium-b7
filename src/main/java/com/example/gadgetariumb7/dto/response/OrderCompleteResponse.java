package com.example.gadgetariumb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class OrderCompleteResponse {
    private Long orderNumber;
    private LocalDate dateOfOrder;
}

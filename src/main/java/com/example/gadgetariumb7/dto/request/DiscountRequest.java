package com.example.gadgetariumb7.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class DiscountRequest {
    private Byte amountOfDiscount;
    @Future
    private LocalDate startDate;
    @Past
    private LocalDate endDate;
    private Long productId;
}

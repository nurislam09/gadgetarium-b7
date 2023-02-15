package com.example.gadgetariumb7.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderInfoResponse {
    private int orderNumber;

    private String phoneNumber;

    private String address;

    public OrderInfoResponse(int orderNumber, String phoneNumber, String address) {
        this.orderNumber = orderNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}

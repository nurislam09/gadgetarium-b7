package com.example.gadgetariumb7.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderPaymentResponse {

    private String fullName;

    private int orderNumber;

    private int totalSum;

    private int countOfProduct;

    private int discount;

    private int totalDiscount;

    private  String productName;

//    private String phoneNumber;
//
//    private String address;

    public OrderPaymentResponse(String fullName, int orderNumber) {
        this.fullName = fullName;
        this.orderNumber = orderNumber;
    }

//    public OrderPaymentResponse(int orderNumber, String phoneNumber, String address) {
//        this.orderNumber = orderNumber;
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//    }
}

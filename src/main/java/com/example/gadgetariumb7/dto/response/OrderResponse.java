package com.example.gadgetariumb7.dto.response;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.enums.OrderType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponse {

    private Long  id;

    private String firstName;

    private String lastName;

    private int orderNumber;

    private LocalDateTime dateOfOrder;

    private int countOfProduct;

    private int totalSum;

    private OrderType orderType;

    private OrderStatus orderStatus;


    public OrderResponse(Long id, String firstName, String lastName, int orderNumber, LocalDateTime dateOfOrder, int countOfProduct, int totalSum, OrderType orderType, OrderStatus orderStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.orderNumber = orderNumber;
        this.dateOfOrder = dateOfOrder;
        this.countOfProduct = countOfProduct;
        this.totalSum = totalSum;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
    }
}

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

    private String fullName;

    private int orderNumber;

    private LocalDateTime dateOfOrder;

    private int countOfProduct;

    private int totalSum;

    private OrderType orderType;

    private OrderStatus orderStatus;


    public OrderResponse(Long id, String fullName, int orderNumber,
                         LocalDateTime dateOfOrder, OrderType orderType, OrderStatus orderStatus) {
        this.id = id;
        this.fullName = fullName;
        this.orderNumber = orderNumber;
        this.dateOfOrder = dateOfOrder;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
    }
}

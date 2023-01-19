package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

      List<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus);

}

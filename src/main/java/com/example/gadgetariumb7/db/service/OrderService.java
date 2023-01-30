package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

import java.util.List;

public interface OrderService {


    List<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus, int page, int size);

    List<OrderResponse> searchOrders(String keyWord, int page, int size);

    Long countByOrderStatus(OrderStatus orderStatus);

    SimpleResponse deleteOrderById(Long id);

    Long getCountOfOrders();


}

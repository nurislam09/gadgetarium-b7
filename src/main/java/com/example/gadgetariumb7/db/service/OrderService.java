package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<OrderResponse> findAllOrders(OrderStatus orderStatus, String keyWord, int page, int size, LocalDate startDate, LocalDate endDate);

    Long countByOrderStatus(OrderStatus orderStatus);

    SimpleResponse deleteOrderById(Long id);

    Long getCountOfOrders();

}

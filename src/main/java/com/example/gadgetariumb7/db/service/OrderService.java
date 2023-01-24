package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

//      Page<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus,Pageable pageable);

      List<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus);

      int countOfOrderStatus (OrderStatus orderStatus);

      void deleteOrderById(Long id);

}

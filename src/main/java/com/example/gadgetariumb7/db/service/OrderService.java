package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {


      List<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus,int page,int size);

      List<OrderResponse> search (String keyWord,int page, int size);

      Long countOfOrderStatus (OrderStatus orderStatus);

      void deleteOrderById(Long id);

}

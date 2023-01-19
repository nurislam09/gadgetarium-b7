package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.service.OrderService;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

   private final OrderService orderService;

   @GetMapping
    public List<OrderResponse> findAll(@RequestParam OrderStatus orderStatus) {
       return orderService.findAllOrdersByStatus(orderStatus);
   }

}

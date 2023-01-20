package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.service.OrderService;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

   private final OrderService orderService;

   @GetMapping
    public Page<OrderResponse> findAll(@RequestParam OrderStatus orderStatus,
                                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC)
                                       Pageable pageable) {
       Pageable newPageable= PageRequest.of(pageable.getPageNumber()-1,5 );
       return orderService.findAllOrdersByStatus(orderStatus,newPageable);
   }
   @DeleteMapping("/{id}")
   public ResponseEntity deleteOrder(@PathVariable Long id) {
       orderService.deleteOrderById(id);
     return ResponseEntity.status(HttpStatus.OK).body("Order with id :"+id+" successfully deleted!");
   }

}

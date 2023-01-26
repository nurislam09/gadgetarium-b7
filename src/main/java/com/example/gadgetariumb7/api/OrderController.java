package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.service.OrderService;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Orders API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "find all orders", description = "Orders with pagination")
    @GetMapping("/pagination/page")
    public List<OrderResponse> findAll(@RequestParam OrderStatus orderStatus,
                                       @RequestParam int page,
                                       @RequestParam int size) {
        return orderService.findAllOrdersByStatus(orderStatus, page, size);
    }

    @Operation(summary = "search", description = "Search orders with pagination")
    @GetMapping()
    public List<OrderResponse> search(@RequestParam String keyWord,
                                      @RequestParam int page,
                                      @RequestParam int size) {
        return orderService.search(keyWord, page, size);
    }

    @Operation(summary = "count orders by status")
    @GetMapping("/count")
    public Long countOfOrderStatus(@RequestParam OrderStatus orderStatus) {
        return orderService.countOfOrderStatus(orderStatus);
    }

    @Operation(summary = "delete order by id")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrderById(id);
    }

}

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Orders API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "find all orders", description = "Orders with pagination and search")
    @GetMapping()
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<OrderResponse>> findAllOrders(@RequestParam(required = false) OrderStatus orderStatus,
                                                             @RequestParam(required = false) String keyWord,
                                                             @RequestParam int page,
                                                             @RequestParam int size,
                                                             @RequestParam(required = false) LocalDate startDate,
                                                             @RequestParam(required = false) LocalDate endDate) {

        List<OrderResponse> orderResponses = orderService.findAllOrders(orderStatus, keyWord, page, size, startDate, endDate);

        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @Operation(summary = "count orders by status")
    @GetMapping("/count")
    @PreAuthorize("hasAuthority('Admin')")
    public Long countOfOrdersByStatus(@RequestParam OrderStatus orderStatus) {
        return orderService.countByOrderStatus(orderStatus);
    }

    @Operation(summary = "count all orders")
    @GetMapping("/count/all")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Long> countAllOrders() {
        Long count = orderService.getCountOfOrders();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Operation(summary = "delete order by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public SimpleResponse deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrderById(id);
    }

}

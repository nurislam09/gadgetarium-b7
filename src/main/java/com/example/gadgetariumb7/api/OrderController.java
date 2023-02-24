package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.service.OrderService;
import com.example.gadgetariumb7.dto.response.OrderInfoResponse;
import com.example.gadgetariumb7.dto.response.OrderPaymentResponse;
import com.example.gadgetariumb7.dto.request.OrderRequest;
import com.example.gadgetariumb7.dto.response.OrderCompleteResponse;
import com.example.gadgetariumb7.dto.response.PaginationOrderResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.dto.response.UserAutofillResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Orders API")
@PreAuthorize("hasAuthority('Admin')")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Find all", description = "Orders with pagination and search")
    @GetMapping()
    public ResponseEntity<PaginationOrderResponse> findAllOrders(@RequestParam OrderStatus orderStatus,
                                                                 @RequestParam(required = false) String keyWord,
                                                                 @RequestParam int page,
                                                                 @RequestParam int size,
                                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return new ResponseEntity<>(orderService.findAllOrders(orderStatus, keyWord, page, size, startDate, endDate), HttpStatus.OK);
    }

    @Operation(summary = "Delete order by id")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrderById(id);
    }

    @Operation(summary = "Update order by orderStatus", description = "In this method we can update orders by order status")
    @PutMapping
    public SimpleResponse update(@RequestParam Long id,
                                 @RequestParam(value = "orderStatus", required = false) OrderStatus orderStatus) {
        return orderService.update(id, orderStatus);
    }

    @Operation(summary = "Get by id order payment info", description = "In this method we can get 1 orders payment info(total sum,total discount, discount)")
    @GetMapping("/{id}")
    public OrderPaymentResponse getOrderPaymentInfo(@RequestParam(value = "orderId", required = false) Long id) {
        return orderService.getOrdersPaymentInfo(id);
    }

    @Operation(summary = "Get by id order info", description = "Get 1 orders info (address, phone number)")
    @GetMapping("/{id}/info")
    public OrderInfoResponse getOrderInfoById(@RequestParam(value = "orderId", required = false) Long id) {
        return orderService.getOrderInfoById(id);
    }

    @Operation(summary = "Autofill", description = "This method return user's information if user is authenticated")
    @GetMapping("/autofill")
    public UserAutofillResponse autofill(){
        return orderService.autofillUserInformation();
    }

    @Operation(summary = "Save order", description = "This method for save order")
    @PostMapping()
    @PreAuthorize("hasAuthority('Customer')")
    public OrderCompleteResponse save(OrderRequest orderRequest){
        return orderService.saveOrder(orderRequest);
    }
}

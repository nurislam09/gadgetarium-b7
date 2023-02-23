package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.repository.OrderRepository;
import com.example.gadgetariumb7.db.service.OrderService;
import com.example.gadgetariumb7.dto.response.*;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public PaginationOrderResponse findAllOrders(OrderStatus orderStatus, String keyWord, int page, int size, LocalDate startDate, LocalDate endDate) {
        List<OrderResponse> orderResponses;
        Page<OrderResponse> orderResponsesPagination;
        PaginationOrderResponse paginationOrderResponse = new PaginationOrderResponse();
        Pageable pageable = PageRequest.of(page - 1, size);

        if (keyWord == null) {
            orderResponsesPagination = orderRepository.findAllOrdersByStatus(orderStatus, pageable);
            orderResponses = orderResponsesPagination.getContent();
        } else {
            orderResponsesPagination = orderRepository.search(keyWord, pageable, orderStatus);
            orderResponses = orderResponsesPagination.getContent();
        }
        try {
            if (startDate != null && endDate != null) {
                orderResponses = orderResponses.stream().filter(o -> o.getDateOfOrder().toLocalDate().isAfter(startDate.minusDays(1)) && o.getDateOfOrder().toLocalDate()
                        .isBefore(endDate.plusDays(1))).toList();
            }
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid date format", ex);
        }
        paginationOrderResponse.setOrderResponses(orderResponses);
        paginationOrderResponse.setCurrentPage(pageable.getPageNumber() + 1);
        paginationOrderResponse.setTotalPage(orderResponsesPagination.getTotalPages());

        Map<OrderStatus,Long> getOrdersByStatus= new HashMap<>();
         getOrdersByStatus.put(OrderStatus.WAITING, orderRepository.countByOrderStatus(OrderStatus.WAITING));
         getOrdersByStatus.put(OrderStatus.IN_PROCESSING,orderRepository.countByOrderStatus(OrderStatus.IN_PROCESSING));
         getOrdersByStatus.put(OrderStatus.ON_THE_WAY,orderRepository.countByOrderStatus(OrderStatus.ON_THE_WAY));
         getOrdersByStatus.put(OrderStatus.DELIVERED,orderRepository.countByOrderStatus(OrderStatus.DELIVERED));
         getOrdersByStatus.put(OrderStatus.CANCEL,orderRepository.countByOrderStatus(OrderStatus.CANCEL));

         paginationOrderResponse.setOrderStatusAndSize(getOrdersByStatus);
        paginationOrderResponse.setCountOfOrders((long) orderRepository.findAll().size());

        return paginationOrderResponse;
    }

    public SimpleResponse deleteOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        if(order.getUser() != null) {
            order.getUser().getOrders().remove(order);
        }
        order.getSubproducts().forEach(x -> x.getOrders().remove(order));
        orderRepository.delete(order);
        return new SimpleResponse("Order successfully deleted!", "ok");
    }

    @Override
    public SimpleResponse update(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order for update not found!"));
        if(orderStatus != null) order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return new SimpleResponse("Order successfully updated", "ok");
    }

    @Override
    public OrderPaymentResponse getOrdersPaymentInfo(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order  not found!"));
        OrderPaymentResponse orderPaymentResponse = new OrderPaymentResponse();
        orderPaymentResponse.setId(order.getId());
        orderPaymentResponse.setFullName(order.getFirstName()+" "+ order.getLastName());
        orderPaymentResponse.setOrderNumber(order.getOrderNumber());
        orderPaymentResponse.setCountOfProduct(order.getCountOfProduct());
        orderPaymentResponse.setTotalSum(order.getTotalSum());
        orderPaymentResponse.setTotalDiscount(order.getTotalDiscount());
        double discount1 = Math.round(((double)order.getTotalDiscount() * 100) /(double)order.getTotalSum());
        orderPaymentResponse.setDiscount(discount1);
        orderPaymentResponse.setTotal(order.getTotalSum()-order.getTotalDiscount());
        orderPaymentResponse.setAddress(order.getUser().getAddress());
        orderPaymentResponse.setPhoneNumber(order.getUser().getPhoneNumber());
        List<String> productsName = new ArrayList<>();
        List<Long> products = new ArrayList<>();
        order.getSubproducts().forEach(s -> {
            Product p = s.getProduct();
            if (!products.contains(p.getId())) {
                products.add(p.getId());
                productsName.add(p.getProductName());
            }
        });
        orderPaymentResponse.setProductsName(productsName);
        orderPaymentResponse.setOrderStatus(order.getOrderStatus());
        return orderPaymentResponse;
    }

}


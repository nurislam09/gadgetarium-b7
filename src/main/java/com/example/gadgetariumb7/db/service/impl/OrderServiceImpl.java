package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.repository.OrderRepository;
import com.example.gadgetariumb7.db.service.OrderService;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<OrderResponse> findAllOrders(OrderStatus orderStatus, String keyWord, int page, int size, LocalDate startDate, LocalDate endDate) {
        List<OrderResponse> orderResponses;
        if (keyWord == null) {
            orderResponses = orderRepository.findAllOrdersByStatus(orderStatus, PageRequest.of(page - 1, size));
        } else {
            orderResponses = orderRepository.search(keyWord, PageRequest.of(page - 1, size)).stream().filter(x -> x.getOrderStatus() == orderStatus).toList();
        }

        if (startDate != null && endDate != null) {
            return orderResponses.stream().filter(o -> o.getDateOfOrder().toLocalDate().isAfter(startDate) && o.getDateOfOrder().toLocalDate()
                    .isBefore(endDate)).toList();
        }

        return orderResponses;
    }

    @Override
    public Long countByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.countByOrderStatus(orderStatus);
    }

    public SimpleResponse deleteOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        order.getUser().getOrders().remove(order);
        order.getSubproducts().forEach(x -> x.getOrders().remove(order));
        orderRepository.delete(order);
        return new SimpleResponse("Order successfully deleted!", "ok");
    }

    @Override
    public Long getCountOfOrders() {
        return orderRepository.getCountOfOrders();
    }

}


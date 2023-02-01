package com.example.gadgetariumb7.db.service.impl;


import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.User;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;


    @Override
    public List<OrderResponse> findAllOrders(OrderStatus orderStatus, String keyWord, int page, int size, LocalDate startDate, LocalDate endDate) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        if (orderStatus != null) {
            orderResponses = orderRepository.findAllOrdersByStatus(orderStatus, PageRequest.of(page - 1, size));
        } else if (keyWord != null) {
            orderResponses = orderRepository.search(keyWord, PageRequest.of(page - 1, size));
        }
        for (OrderResponse orderResponse : orderResponses) {
            User user = orderRepository.findById(orderResponse.getId()).orElseThrow(() -> new NotFoundException("Order not found")).getUser();
            int orderCount = user.getBasketList().stream().mapToInt(Product::getOrderCount).sum();
            int totalSum = user.getBasketList().stream().filter(p -> p.getDiscount() == null).map(p -> p.getOrderCount() * p.getProductPrice())
                    .reduce(0, Integer::sum);
            int totalSumWithDiscount = user.getBasketList().stream().filter(p -> p.getDiscount() != null)
                    .map(p -> p.getOrderCount() * (p.getProductPrice() - (p.getProductPrice() * p.getDiscount().getAmountOfDiscount() / 100)))
                    .reduce(0, Integer::sum);

            int totalSumAndTotalSumWithDiscount = totalSum + totalSumWithDiscount;

            if (totalSumWithDiscount != 0 && totalSum != 0) {
                orderResponse.setTotalSum(totalSumAndTotalSumWithDiscount);
            } else if (totalSumWithDiscount != 0) {
                orderResponse.setTotalSum(totalSumWithDiscount);
            } else {
                orderResponse.setTotalSum(totalSum);
            }
            orderResponse.setCountOfProduct(orderCount);

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
        orderRepository.delete(order);
        return new SimpleResponse("Order successfully deleted!", "ok");
    }

    @Override
    public Long getCountOfOrders() {
        return orderRepository.getCountOfOrders();
    }

}


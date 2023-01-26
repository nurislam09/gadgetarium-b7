package com.example.gadgetariumb7.db.service;


import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.repository.OrderRepository;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    public List<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus, int page, int size) {
        List<OrderResponse> orderResponses = orderRepository.findAllOrdersByStatus(orderStatus, PageRequest.of(page - 1, size));
        for (OrderResponse orderResponse : orderResponses) {
            User user = orderRepository.getById(orderResponse.getId()).getUser();

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

        return orderResponses;

    }


    public List<OrderResponse> search(String keyWord, int page, int size) {
        return orderRepository.search(keyWord, PageRequest.of(page - 1, size));
    }

    public Long countOfOrderStatus(OrderStatus orderStatus) {
        return orderRepository.countByOrderStatus(orderStatus);
    }

    public SimpleResponse deleteOrderById(Long id) {
        orderRepository.deleteById(id);
        return new SimpleResponse("Order by id "+ id+" successfully deleted!", "ok");
    }



}


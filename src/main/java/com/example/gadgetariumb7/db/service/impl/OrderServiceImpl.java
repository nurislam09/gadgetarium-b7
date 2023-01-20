package com.example.gadgetariumb7.db.service.impl;


import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.enums.DeliveryStatus;
import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.enums.OrderType;
import com.example.gadgetariumb7.db.enums.Payment;
import com.example.gadgetariumb7.db.repository.OrderRepository;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.service.OrderService;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;


//    @Override
//    public List<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus) {
//        List<OrderResponse>orderResponses= orderRepository.findAllOrdersByStatus(orderStatus);
//        for (OrderResponse orderResponse:orderResponses) {
//            Order order = orderRepository.getById(orderResponse.getId());
//            orderResponse.setCountOfProduct(order.getProducts().size());
//            int totalSum=0;
//            for (Product product: order.getProducts()) {
//                if(product.getDiscount()!= null) {
//                    totalSum += product.getProductPrice()-((product.getProductPrice() * product.getDiscount()
//                            .getAmountOfDiscount())/100);
//                } else totalSum += product.getProductPrice();
//            }
//            orderResponse.setTotalSum(totalSum);
//        }

//        return orderResponses;


        @Override
        public List<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus) {
        List<OrderResponse> orderResponses = orderRepository.findAllOrdersByStatus(orderStatus);
        for (OrderResponse orderResponse : orderResponses) {
            Optional<Order> orderOptional = orderRepository.findById(orderResponse.getId());
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                int countOfProduct = order.getProducts().size();
                int totalSum = 0;
                for (Product product : order.getProducts()) {
                    if (product.getDiscount() != null) {
                        totalSum += product.getProductPrice() - ((product.getProductPrice() * product.getDiscount()
                                .getAmountOfDiscount()) / 100);
                    } else totalSum += product.getProductPrice();
                }
                    orderResponse.setCountOfProduct(countOfProduct);
                    orderResponse.setTotalSum(totalSum);
                }
            }
            return orderResponses;

        }


//        @PostConstruct
//        private void initOrders () {
//
//           Product product = new Product("Apple", 95000);
//
//
//            Order order = new Order();
//            order.setFirstName("Asan");
//            order.setLastName("Asanov");
//            order.setDateOfOrder(LocalDateTime.now());
//            order.setOrderStatus(OrderStatus.CANCEL);
//            order.setOrderNumber(1);
//            order.setEmail("a@gmail.com");
//            order.setAddress("Chyi");
//            order.setOrderType(OrderType.DELIVERY);
//            order.setDeliveryStatus(DeliveryStatus.WAITING);
//            order.setPayment(Payment.PAYMENT_WITH_CARD);
//            order.setPhoneNumber("0556897256");
//
//            order.setProducts(Arrays.asList(product));
//            product.setOrders(Arrays.asList(order));
//
//            order.setCountOfProduct(order.getProducts().size());
//            order.setTotalSum(product.getProductPrice() * order.getProducts().size());
//        }
//
   }


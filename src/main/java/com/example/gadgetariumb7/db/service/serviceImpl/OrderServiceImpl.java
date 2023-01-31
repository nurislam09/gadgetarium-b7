package com.example.gadgetariumb7.db.service.serviceImpl;


import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.enums.DeliveryStatus;
import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.enums.OrderType;
import com.example.gadgetariumb7.db.enums.Payment;
import com.example.gadgetariumb7.db.repository.OrderRepository;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.repository.UserRepository;
import com.example.gadgetariumb7.db.service.OrderService;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;
    @Override
    public List<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus, int page, int size) {
        List<OrderResponse> orderResponses = orderRepository.findAllOrdersByStatus(orderStatus, PageRequest.of(page - 1, size));
        for (OrderResponse orderResponse : orderResponses) {
            User user = orderRepository.getById(orderResponse.getId()).getUser();
            calculateOrderInfo(orderResponse,user);
        }

        return orderResponses;

    }

    @Override
    public List<OrderResponse> searchOrders(String keyWord, int page, int size) {
        List<OrderResponse> orderResponses = orderRepository.search(keyWord, PageRequest.of(page - 1, size));
        for (OrderResponse orderResponse : orderResponses) {
            User user = orderRepository.findById(orderResponse.getId()).orElseThrow(() -> new NotFoundException("Order not found")).getUser();

            calculateOrderInfo(orderResponse, user);
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

    public void calculateOrderInfo(OrderResponse orderResponse, User user) {
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

//        @PostConstruct
//        private void initOrders () {
//
//            User user = userRepository.findById(1L).get();
//
//
//            Product product1 = new Product("Iphone", 5000, 5);
//            Product product2 = new Product("Sumsung", 8000, 10);
//            Product product3 = new Product("MI", 40000, 15);
//            Product product4 = new Product("Apple", 30000, 30);
//
////            Product product1 = productRepository.findById(1L).orElseThrow(()->new NotFoundException("Product not found"));
////            Product product2 = productRepository.findById(2L).orElseThrow(()->new NotFoundException("Product not found"));
////
//
//            user.setBasketList(Arrays.asList(product1, product2));
//
//            Order order = new Order();
//            order.setFirstName(user.getFirstName());
//            order.setLastName(user.getLastName());
//            order.setDateOfOrder(LocalDateTime.now());
//            order.setOrderStatus(OrderStatus.CANCEL);
//            order.setOrderNumber(1);
//            order.setEmail(user.getEmail());
//            order.setAddress("Chyi");
//            order.setOrderType(OrderType.DELIVERY);
//            order.setDeliveryStatus(DeliveryStatus.WAITING);
//            order.setPayment(Payment.PAYMENT_WITH_CARD);
//            order.setPhoneNumber(user.getPhoneNumber());
////            order.setProducts(Arrays.asList(product1, product2));
//
//            order.setUser(user);
//            user.setOrders(Arrays.asList(order));
//
//            order.setProducts(user.getBasketList());
//            user.getBasketList().forEach(x -> x.setOrders(Arrays.asList(order)));
//
//            userRepository.save(user);
//            orderRepository.save(order);
//            productRepository.save(product1);
//            productRepository.save(product2);
//        }

}


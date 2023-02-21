package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.entity.Subproduct;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.repository.OrderRepository;
import com.example.gadgetariumb7.db.repository.SubproductRepository;
import com.example.gadgetariumb7.db.repository.UserRepository;
import com.example.gadgetariumb7.db.service.OrderService;
import com.example.gadgetariumb7.dto.response.*;
import com.example.gadgetariumb7.dto.request.OrderRequest;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final SubproductRepository subproductRepository;
    private int orderGenerateNumber = 100006;

    private Optional<User> getAuthenticateUserForAutofill() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

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

        if (startDate != null && endDate != null) {
            orderResponses = orderResponses.stream().filter(o -> o.getDateOfOrder().toLocalDate().isAfter(startDate) && o.getDateOfOrder().toLocalDate()
                    .isBefore(endDate)).toList();
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
        order.getUser().getOrders().remove(order);
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
        for (Subproduct subproduct:order.getSubproducts()) {
            orderPaymentResponse.setProductName(subproduct.getProduct().getProductName());
            int discount = 0;
            if (subproduct.getProduct().getDiscount().getAmountOfDiscount() != null) {
                discount = (order.getTotalDiscount() * 100) / order.getTotalSum();
                orderPaymentResponse.setDiscount(discount);
            }
        }
        return orderPaymentResponse;
    }

    @Override
    public OrderInfoResponse getOrderInfoById(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    OrderInfoResponse orderInfoResponse = new OrderInfoResponse();
                    orderInfoResponse.setOrderNumber(order.getOrderNumber());
                    orderInfoResponse.setPhoneNumber(order.getPhoneNumber());
                    orderInfoResponse.setAddress(order.getAddress());
                    return orderInfoResponse;
                })
                .orElseThrow(() -> new NotFoundException("Order not found!"));
    }


    @Override
    public UserAutofillResponse autofillUserInformation() {
        if (getAuthenticateUserForAutofill().isPresent()){
            User user = getAuthenticateUserForAutofill().get();
            return new UserAutofillResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getAddress());
        } else {
            throw new NotFoundException("User is not authenticate");
        }
    }

    @Override
    public OrderCompleteResponse saveOrder(OrderRequest req) {
        User user = getAuthenticateUserForAutofill().orElseThrow(() -> new NotFoundException("User not found"));
        List<Subproduct> subproducts = req.getSubproductsId().stream().map(s -> subproductRepository.findById(s).orElseThrow(() -> new NotFoundException(String.format("Subproduct with id %d not found", s)))).toList();
        Order order = new Order(req.getFirstName(), req.getLastName(), req.getEmail(), req.getPhoneNumber(), req.getAddress(), req.getCountOfProduct(), req.getTotalSum(), req.getTotalDiscount(), req.getPayment(), req.getOrderType(), subproducts, user, orderGenerateNumber);
        subproducts.forEach(x -> {
            if (user.getBasketList().containsKey(x)){
                user.getBasketList().remove(x);
            } else {
                throw new NotFoundException(String.format("Subproduct not exist in user's basket list", x.getId(), user.getId()));
            }
        });
        orderRepository.save(order);
        orderGenerateNumber++;
        return new OrderCompleteResponse(order.getOrderNumber(), order.getDateOfOrder());
    }
}


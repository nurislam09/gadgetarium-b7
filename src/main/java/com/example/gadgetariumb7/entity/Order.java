package com.example.gadgetariumb7.entity;

import com.example.gadgetariumb7.entity.enums.DeliveryStatus;
import com.example.gadgetariumb7.entity.enums.OrderStatus;
import com.example.gadgetariumb7.entity.enums.OrderType;

import com.example.gadgetariumb7.entity.enums.Payment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq",allocationSize = 1)
    private Long id;

    private String  firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private int count;

    private int totalSum;

    private int totalDiscount;

    private int orderNumber;

    private LocalDateTime dateOfOrder;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = FetchType.EAGER)
    private User user;

    @ManyToMany(cascade = {MERGE, DETACH, REFRESH}, mappedBy = "orders")
    private List<Product> products;


}

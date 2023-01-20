package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {



//    @Query(value = "select count(p) as countOfProduct , sum(p.productPrice) as totalSum
//    from Product p join p.orders o where o.id = :orderId")
//    List<Object[]> findCountOfProductAndTotalSum(@Param("orderId") Long orderId);

     @Query("select new com.example.gadgetariumb7.dto.response.OrderResponse(o.id," +
             "o.firstName," +
             "o.lastName," +
             "o.orderNumber," +
             "o.dateOfOrder," +
             "o.countOfProduct ," +
             "o.totalSum ," +
             "o.orderType," +
             "o.orderStatus) from Order o where o.orderStatus=:orderStatus")
    List<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus);
}
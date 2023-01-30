package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.dto.response.OrderResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


     @Query("select new com.example.gadgetariumb7.dto.response.OrderResponse(o.id," +
             "concat(o.firstName,' ', o.lastName ), "+
             "o.orderNumber," +
             "o.dateOfOrder," +
             "o.orderType," +
             "o.orderStatus) from Order o where o.orderStatus = :orderStatus")
     List<OrderResponse> findAllOrdersByStatus(OrderStatus orderStatus,Pageable pageable);




    @Query ("select  count (o) from  Order o where  o.orderStatus = :orderStatus")
    Long countByOrderStatus (OrderStatus orderStatus);

    @Query ("select count (o) from  Order  o")
    Long getCountOfOrders();


    @Query("select new com.example.gadgetariumb7.dto.response.OrderResponse(o.id," +
            "concat(o.firstName,' ', o.lastName ), "+
            "o.orderNumber," +
            "o.dateOfOrder," +
            "o.countOfProduct,"+
            "o.totalSum,"+
            "o.orderType," +
            "o.orderStatus) from Order o where  concat( concat(o.firstName,' ', o.lastName ),' ', o.orderNumber," +
            " ' ', o.orderType,' ', o.orderStatus) like %:keyWord%")
    List<OrderResponse> search(@Param("keyWord") String keyWord, Pageable pageable);




}


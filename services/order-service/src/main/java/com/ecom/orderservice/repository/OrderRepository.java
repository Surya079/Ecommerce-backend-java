package com.ecom.orderservice.repository;

import com.ecom.orderservice.controller.dto.OrderLineResponseDto;
import com.ecom.orderservice.entity.Order;
import com.ecom.orderservice.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer > {
    List<OrderLine> findAllOrdersById(Integer orderId);
}

package com.ecom.orderservice.mapper;

import com.ecom.orderservice.controller.dto.OrderLineRequest;
import com.ecom.orderservice.entity.Order;
import com.ecom.orderservice.entity.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {

        return OrderLine.builder()
                .id(orderLineRequest.id())
                .quantity(orderLineRequest.quantity())
                .order(
                        Order.builder()
                                .id(orderLineRequest.orderId())
                                .build()
                )
                .productId(orderLineRequest.productId())
                .build();
    }
}

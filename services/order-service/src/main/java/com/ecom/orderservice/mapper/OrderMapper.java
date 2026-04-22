package com.ecom.orderservice.mapper;

import com.ecom.orderservice.controller.dto.OrderLineResponseDto;
import com.ecom.orderservice.controller.dto.OrderRequestDto;
import com.ecom.orderservice.controller.dto.OrderResponseDto;
import com.ecom.orderservice.entity.Order;
import com.ecom.orderservice.entity.OrderLine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequestDto requestDto){
        return Order.builder()
                .id(requestDto.id())
                .customerId(requestDto.customerId())
                .reference(requestDto.reference())
                .totalAmount(requestDto.amount())
                .paymentMethod(requestDto.paymentMethod())
                .build();
    }

    public OrderResponseDto fromOrder(Order order) {
            return new OrderResponseDto(
                    order.getId(),
                    order.getReference(),
                    order.getTotalAmount(),
                    order.getPaymentMethod(),
                    order.getCustomerId()
            );
    }

    public OrderLineResponseDto toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponseDto(
                orderLine.getId(), orderLine.getQuantity()
        );
    }
}

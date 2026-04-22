package com.ecom.orderservice.controller;

import com.ecom.orderservice.controller.dto.OrderLineResponseDto;
import com.ecom.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-lines")
@RequiredArgsConstructor
public class OrderLineController {

    private final OrderService orderService;

    @GetMapping("/order/{order-id}")
    public ResponseEntity<List<OrderLineResponseDto>> getAllOrdersByOrderId (
            @PathVariable("order-id") Integer orderId
    ){
        return ResponseEntity.ok(orderService.findAllOrderByOrderId(orderId));
    }
}

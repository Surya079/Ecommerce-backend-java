package com.ecom.orderservice.controller;

import com.ecom.orderservice.controller.dto.OrderRequestDto;
import com.ecom.orderservice.controller.dto.OrderResponseDto;
import com.ecom.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderRequestDto requestDto
    ){
        return ResponseEntity.ok(service.createOrder(requestDto));
    }


    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){
        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponseDto> findById (
            @PathVariable("order-id") Integer orderId
    ){
        return ResponseEntity.ok(service.findById(orderId));
    }






}

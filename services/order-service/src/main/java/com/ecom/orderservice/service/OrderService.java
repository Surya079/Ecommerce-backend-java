package com.ecom.orderservice.service;

import com.ecom.orderservice.controller.dto.*;
import com.ecom.orderservice.exception.BusinessException;
import com.ecom.orderservice.exception.OrderNotFoundException;
import com.ecom.orderservice.kafka.OrderProducer;
import com.ecom.orderservice.kafka.dto.OrderConfirmationDto;
import com.ecom.orderservice.mapper.OrderMapper;
import com.ecom.orderservice.openfeign.CustomerClient;
import com.ecom.orderservice.openfeign.PaymentClient;
import com.ecom.orderservice.openfeign.ProductClient;
import com.ecom.orderservice.openfeign.dto.PaymentRequestDto;
import com.ecom.orderservice.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequestDto requestDto) {
        var customer = this.customerClient.findById(requestDto.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with provided customer id"));
        log.info("customer details "+customer.toString());
        var purchasedProducts = this.productClient.purchaseProducts(requestDto.products());

        var orders = this.orderRepository.save(orderMapper.toOrder(requestDto));

        for(PurchaseRequestDto purchaseRequestDto: requestDto.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            orders.getId(),
                            purchaseRequestDto.productId(),
                            purchaseRequestDto.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequestDto(
                requestDto.id(),
                requestDto.amount(),
                requestDto.paymentMethod(),
                orders.getId(),
                orders.getReference(),
                customer
        );


        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmationDto(
                        requestDto.reference(),
                        requestDto.amount(),
                        requestDto.paymentMethod(),
                        customer,
                        purchasedProducts

                )
        );

    return orders.getId();
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponseDto findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new OrderNotFoundException(String.format("No order found with provided ID :: ", orderId)));
    }

    public List<OrderLineResponseDto> findAllOrderByOrderId(Integer orderId) {
        return orderRepository.findAllOrdersById(orderId)
                .stream()
                .map(orderMapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}

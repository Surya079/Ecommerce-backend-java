package com.ecom.orderservice.controller.dto;

import com.ecom.orderservice.entity.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponseDto (
    Integer id,
    String reference,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    String customerId
){
}

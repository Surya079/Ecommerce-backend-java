package com.ecom.orderservice.openfeign.dto;

import com.ecom.orderservice.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequestDto(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}

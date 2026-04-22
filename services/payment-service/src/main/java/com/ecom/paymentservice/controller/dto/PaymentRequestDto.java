package com.ecom.paymentservice.controller.dto;

import com.ecom.paymentservice.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequestDto(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponseDto customer
) {
}

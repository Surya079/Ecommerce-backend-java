package com.ecom.paymentservice.kafka.dto;

import com.ecom.paymentservice.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequestDto(

        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {
}

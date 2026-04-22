package com.ecom.notificationservice.kafka.payment.dto;

import com.ecom.notificationservice.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentConfirmation(

        String orderReference,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        String customerFirstName,

        String customerLastName,

        String customerEmail
) {
}

package com.ecom.notificationservice.kafka.order.dto;

import com.ecom.notificationservice.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
        List<ProductResponseDto> products

){
}

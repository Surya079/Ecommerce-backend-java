package com.ecom.orderservice.kafka.dto;

import com.ecom.orderservice.entity.PaymentMethod;
import com.ecom.orderservice.openfeign.dto.CustomerResponse;
import com.ecom.orderservice.openfeign.dto.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmationDto(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
        List<PurchaseResponse> products
) {
}

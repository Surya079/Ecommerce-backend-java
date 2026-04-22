package com.ecom.paymentservice.mapper;

import com.ecom.paymentservice.controller.dto.PaymentRequestDto;
import com.ecom.paymentservice.entity.Payment;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(@Valid PaymentRequestDto paymentRequestDto) {
        return Payment.builder()
                .id(paymentRequestDto.id())
                .orderId(paymentRequestDto.orderId())
                .paymentMethod(paymentRequestDto.paymentMethod())
                .amount(paymentRequestDto.amount())
                .build();
    }
}

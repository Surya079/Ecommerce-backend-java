package com.ecom.paymentservice.service;

import com.ecom.paymentservice.controller.dto.PaymentRequestDto;
import com.ecom.paymentservice.kafka.NotificationProducer;
import com.ecom.paymentservice.kafka.dto.PaymentNotificationRequestDto;
import com.ecom.paymentservice.mapper.PaymentMapper;
import com.ecom.paymentservice.repository.PaymentRepository;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper mapper;

    private final NotificationProducer notificationProducer;

    public Integer createPayment(@Valid PaymentRequestDto paymentRequestDto) {
        var payment = paymentRepository.save(mapper.toPayment(paymentRequestDto));

        notificationProducer.sendNotification(
                new PaymentNotificationRequestDto(
                        paymentRequestDto.orderReference(),
                        paymentRequestDto.amount(),
                        paymentRequestDto.paymentMethod(),
                        paymentRequestDto.customer().firstName(),
                        paymentRequestDto.customer().lastName(),
                        paymentRequestDto.customer().email()
                )
        );

        return payment.getId();
    }
}

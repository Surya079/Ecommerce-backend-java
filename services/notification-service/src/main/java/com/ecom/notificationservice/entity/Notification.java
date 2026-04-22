package com.ecom.notificationservice.entity;

import com.ecom.notificationservice.kafka.order.dto.OrderConfirmation;
import com.ecom.notificationservice.kafka.payment.dto.PaymentConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Notification {

    @Id
    private String id;

    private NotificationType type;

    private LocalDateTime notificationDate;

    private OrderConfirmation orderConfirmation;

    private PaymentConfirmation paymentConfirmation;

}

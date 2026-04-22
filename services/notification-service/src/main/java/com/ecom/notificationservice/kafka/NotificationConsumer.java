package com.ecom.notificationservice.kafka;

import com.ecom.notificationservice.entity.Notification;
import com.ecom.notificationservice.kafka.order.dto.OrderConfirmation;
import com.ecom.notificationservice.kafka.payment.dto.PaymentConfirmation;
import com.ecom.notificationservice.repository.NotificationRepository;
import com.ecom.notificationservice.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.ecom.notificationservice.entity.NotificationType.ORDER_CONFIRMATION;
import static com.ecom.notificationservice.entity.NotificationType.PAYMENT_CONFIRMATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    // ================= PAYMENT =================
    @KafkaListener(
            topics = "payment-topic",
            containerFactory = "paymentKafkaListenerContainerFactory"
    )
    public void consumePaymentSuccessNotification(PaymentConfirmation payment) {

        log.info("Received payment message: {}", payment);

        try {
            String firstName = payment.customerFirstName() != null ? payment.customerFirstName() : "";
            String lastName = payment.customerLastName() != null ? payment.customerLastName() : "";
            String email = payment.customerEmail();

            if (email == null || email.isEmpty()) {
                log.error("Email is null in payment event → skipping email");
                return;
            }

            String customerName = firstName + " " + lastName;

            notificationService.sendPaymentSuccessfulEmail(
                    email,
                    customerName.trim(),
                    payment.amount(),
                    payment.orderReference()
            );

        } catch (Exception e) {
            log.error("Email sending failed (payment)", e);
        }

        try {
            notificationRepository.save(
                    Notification.builder()
                            .type(PAYMENT_CONFIRMATION)
                            .notificationDate(LocalDateTime.now())
                            .paymentConfirmation(payment)
                            .build()
            );
        } catch (Exception e) {
            log.warn("Mongo save failed (payment), skipping: {}", e.getMessage());
        }
    }

    // ================= ORDER =================
    @KafkaListener(
            topics = "order-topic",
            containerFactory = "orderKafkaListenerContainerFactory"
    )
    public void consumeOrderConfirmationNotification(OrderConfirmation order) {

        log.info("Received order message: {}", order);

        try {


            String firstName = order.customerResponse().firstName() != null ? order.customerResponse().firstName() : "";
            String lastName = order.customerResponse().lastName() != null ? order.customerResponse().lastName() : "";
            String email = order.customerResponse().email();

            if (email == null || email.isEmpty()) {
                log.error("Email is null in order event → skipping email");
                return;
            }

            String customerName = (firstName + " " + lastName).trim();

            notificationService.sendOrderConfirmationEmail(
                    email,
                    firstName,
                    order.totalAmount(),
                    order.orderReference(),
                    order.products()
            );

        } catch (Exception e) {
            log.error("Email sending failed (order)", e);
        }

        try {
            notificationRepository.save(
                    Notification.builder()
                            .type(ORDER_CONFIRMATION)
                            .notificationDate(LocalDateTime.now())
                            .orderConfirmation(order)
                            .build()
            );
        } catch (Exception e) {
            log.warn("Mongo save failed (order), skipping: {}", e.getMessage());
        }
    }
}
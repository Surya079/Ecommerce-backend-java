package com.ecom.paymentservice.kafka;

import com.ecom.paymentservice.kafka.dto.PaymentNotificationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, PaymentNotificationRequestDto> kafkaTemplate;

    public void sendNotification(PaymentNotificationRequestDto requestDto){
        log.info("Sending Notification with body <{}>", requestDto);
        Message<PaymentNotificationRequestDto> message = MessageBuilder
                .withPayload(requestDto)
                .setHeader(KafkaHeaders.TOPIC, "payment-topic")
                .build();
        kafkaTemplate.send(message).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send message", ex);
            } else {
                log.info("Message sent successfully");
            }
        });
    }


}

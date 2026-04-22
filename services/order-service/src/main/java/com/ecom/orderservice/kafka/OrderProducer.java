package com.ecom.orderservice.kafka;

import com.ecom.orderservice.kafka.dto.OrderConfirmationDto;
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
public class OrderProducer {

    private final KafkaTemplate<String, OrderConfirmationDto> kafkaTemplate;

    public void sendOrderConfirmation (OrderConfirmationDto confirmationDto){
        log.info("Orders details: " +confirmationDto);
        log.info("Sending order confirmation");
        Message<OrderConfirmationDto> message = MessageBuilder
                .withPayload(confirmationDto)
                .setHeader(KafkaHeaders.TOPIC, "order-topic")
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

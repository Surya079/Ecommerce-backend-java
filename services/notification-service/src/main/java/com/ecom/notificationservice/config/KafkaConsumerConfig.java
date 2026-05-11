package com.ecom.notificationservice.config;

import com.ecom.notificationservice.kafka.order.dto.OrderConfirmation;
import com.ecom.notificationservice.kafka.payment.dto.PaymentConfirmation;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private Map<String, Object> commonProps() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "ms-kafka:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notificationGroup");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderConfirmation> orderKafkaListenerContainerFactory() {

        JsonDeserializer<OrderConfirmation> deserializer =
                new JsonDeserializer<>(OrderConfirmation.class);

        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(false);

        DefaultKafkaConsumerFactory<String, OrderConfirmation> factory =
                new DefaultKafkaConsumerFactory<>(
                        commonProps(),
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, OrderConfirmation> container =
                new ConcurrentKafkaListenerContainerFactory<>();

        container.setConsumerFactory(factory);

        return container;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentConfirmation> paymentKafkaListenerContainerFactory() {

        JsonDeserializer<PaymentConfirmation> deserializer =
                new JsonDeserializer<>(PaymentConfirmation.class);

        deserializer.addTrustedPackages("*");

        DefaultKafkaConsumerFactory<String, PaymentConfirmation> factory =
                new DefaultKafkaConsumerFactory<>(
                        commonProps(),
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, PaymentConfirmation> container =
                new ConcurrentKafkaListenerContainerFactory<>();

        container.setConsumerFactory(factory);

        return container;
    }
}
package com.ecom.notificationservice.repository;

import com.ecom.notificationservice.entity.Notification;
import com.ecom.notificationservice.kafka.payment.dto.PaymentConfirmation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

}

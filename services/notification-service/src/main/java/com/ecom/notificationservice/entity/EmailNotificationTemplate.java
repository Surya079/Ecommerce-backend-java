package com.ecom.notificationservice.entity;

import lombok.Getter;

public enum EmailNotificationTemplate {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation");

    @Getter
    private final String template;

    @Getter
    private final String subject;

    EmailNotificationTemplate(
            String template, String  subject
    ){
        this.template = template;
        this.subject = subject;
    };



}

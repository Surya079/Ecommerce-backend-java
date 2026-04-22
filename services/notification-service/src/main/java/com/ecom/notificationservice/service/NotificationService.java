package com.ecom.notificationservice.service;

import com.ecom.notificationservice.entity.EmailNotificationTemplate;
import com.ecom.notificationservice.kafka.order.dto.ProductResponseDto;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class NotificationService {



    private final SpringTemplateEngine templateEngine;


    private final JavaMailSender mailSender;

    @PostConstruct
    public void test() {
        System.out.println("MAIL BEAN: " + mailSender);
    }

    @Async
    public void sendPaymentSuccessfulEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());


        messageHelper.setFrom("contact@surya.com");
        final String templateName = EmailNotificationTemplate.PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();

        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();

        context.setVariables(variables);
        messageHelper.setSubject(EmailNotificationTemplate.PAYMENT_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);

            log.info(String.format("INFO - Email successfully send to %s with template %s", destinationEmail, templateName));


        }catch (MessagingException e){
            log.warn("WARNING - Cannot send a email to %s ", destinationEmail);
        }
    }


    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<ProductResponseDto> products
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());


        mimeMessage.setFrom("contact@surya.com");
        final String templateName = EmailNotificationTemplate.ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();

        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);



        Context context = new Context();

        context.setVariables(variables);
        messageHelper.setSubject(EmailNotificationTemplate.ORDER_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);

            log.info(String.format("INFO - Email successfully send to %s with template %s", destinationEmail, templateName));


        }catch (MessagingException e){
            log.warn("WARNING - Cannot send a email to %s ", destinationEmail);
        }
    }
}

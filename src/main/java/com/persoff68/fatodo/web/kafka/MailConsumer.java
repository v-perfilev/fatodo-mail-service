package com.persoff68.fatodo.web.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.exception.KafkaException;
import com.persoff68.fatodo.mapper.ActivationMapper;
import com.persoff68.fatodo.mapper.FeedbackMapper;
import com.persoff68.fatodo.mapper.NotificationMapper;
import com.persoff68.fatodo.mapper.ResetPasswordMapper;
import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.Feedback;
import com.persoff68.fatodo.model.Notification;
import com.persoff68.fatodo.model.ResetPassword;
import com.persoff68.fatodo.model.dto.ActivationDTO;
import com.persoff68.fatodo.model.dto.FeedbackDTO;
import com.persoff68.fatodo.model.dto.NotificationDTO;
import com.persoff68.fatodo.model.dto.ResetPasswordDTO;
import com.persoff68.fatodo.service.MailService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@ConditionalOnPropertyNotNull(value = "kafka.bootstrapAddress")
public class MailConsumer {

    private static final String MAIL_AUTH_TOPIC = "mail_auth";
    private static final String MAIL_NOTIFICATION_TOPIC = "mail_notification";
    private static final String MAIL_FEEDBACK_TOPIC = "mail_feedback";

    private final MailService mailService;
    private final ActivationMapper activationMapper;
    private final ResetPasswordMapper resetPasswordMapper;
    private final NotificationMapper notificationMapper;
    private final FeedbackMapper feedbackMapper;
    private final ObjectMapper objectMapper;

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = MAIL_AUTH_TOPIC, containerFactory = "authContainerFactory")
    public void sendAuthMail(@Payload String value, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        switch (key) {
            case "activation" -> handleActivation(value);
            case "reset-password" -> handleResetPassword(value);
            default -> throw new KafkaException();
        }
        resetLatch();
    }

    @KafkaListener(topics = MAIL_NOTIFICATION_TOPIC, containerFactory = "notificationContainerFactory")
    public void sendNotificationMail(NotificationDTO notificationDTO) {
        Notification notification = notificationMapper.dtoToPojo(notificationDTO);
        mailService.sendNotificationEmail(notification);
        resetLatch();
    }

    @KafkaListener(topics = MAIL_FEEDBACK_TOPIC, containerFactory = "feedbackContainerFactory")
    public void sendNotificationMail(FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackMapper.dtoToPojo(feedbackDTO);
        mailService.sendFeedbackEmail(feedback);
        resetLatch();
    }

    private void handleActivation(String value) {
        try {
            ActivationDTO activationDTO = objectMapper.readValue(value, ActivationDTO.class);
            Activation activation = activationMapper.dtoToPojo(activationDTO);
            mailService.sendActivationEmail(activation);
        } catch (JsonProcessingException e) {
            throw new KafkaException();
        }
    }

    private void handleResetPassword(String value) {
        try {
            ResetPasswordDTO resetPasswordDTO = objectMapper.readValue(value, ResetPasswordDTO.class);
            ResetPassword resetPassword = resetPasswordMapper.dtoToPojo(resetPasswordDTO);
            mailService.sendResetPasswordEmail(resetPassword);
        } catch (JsonProcessingException e) {
            throw new KafkaException();
        }
    }

    private void resetLatch() {
        this.latch.countDown();
        this.latch = new CountDownLatch(1);
    }

}

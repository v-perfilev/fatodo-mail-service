package com.persoff68.fatodo.kafka.consumer;

import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.Notification;
import com.persoff68.fatodo.model.ResetPassword;
import com.persoff68.fatodo.model.dto.ActivationDTO;
import com.persoff68.fatodo.model.dto.NotificationDTO;
import com.persoff68.fatodo.model.dto.ResetPasswordDTO;
import com.persoff68.fatodo.model.mapper.ActivationMapper;
import com.persoff68.fatodo.model.mapper.NotificationMapper;
import com.persoff68.fatodo.model.mapper.ResetPasswordMapper;
import com.persoff68.fatodo.service.MailService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@ConditionalOnPropertyNotNull(value = "kafka.bootstrapAddress")
public class MailConsumer {

    private final MailService mailService;
    private final ActivationMapper activationMapper;
    private final ResetPasswordMapper resetPasswordMapper;
    private final NotificationMapper notificationMapper;

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "mail_activation", containerFactory = "activationContainerFactory")
    public void sendActivationMail(ActivationDTO activationDTO) {
        Activation activation = activationMapper.dtoToPojo(activationDTO);
        mailService.sendActivationEmail(activation);
        resetLatch();
    }

    @KafkaListener(topics = "mail_resetPassword", containerFactory = "resetPasswordContainerFactory")
    public void sendResetPasswordMail(ResetPasswordDTO resetPasswordDTO) {
        ResetPassword resetPassword = resetPasswordMapper.dtoToPojo(resetPasswordDTO);
        mailService.sendResetPasswordEmail(resetPassword);
        resetLatch();
    }

    @KafkaListener(topics = "mail_notification", containerFactory = "notificationContainerFactory")
    public void sendNotificationMail(NotificationDTO notificationDTO) {
        Notification notification = notificationMapper.dtoToPojo(notificationDTO);
        mailService.sendNotificationEmail(notification);
        resetLatch();
    }

    private void resetLatch() {
        this.latch.countDown();
        this.latch = new CountDownLatch(1);
    }

}

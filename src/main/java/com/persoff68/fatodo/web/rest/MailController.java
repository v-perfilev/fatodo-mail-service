package com.persoff68.fatodo.web.rest;

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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(MailController.ENDPOINT)
public class MailController {
    static final String ENDPOINT = "/api/mail";

    private final MailService mailService;
    private final ActivationMapper activationMapper;
    private final ResetPasswordMapper resetPasswordMapper;
    private final NotificationMapper notificationMapper;
    private final FeedbackMapper feedbackMapper;

    @PostMapping(value = "/activation")
    public ResponseEntity<Void> sendActivationMail(@RequestBody ActivationDTO activationDTO) {
        Activation activation = activationMapper.dtoToPojo(activationDTO);
        mailService.sendActivationEmail(activation);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<Void> sendResetPasswordMail(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        ResetPassword resetPassword = resetPasswordMapper.dtoToPojo(resetPasswordDTO);
        mailService.sendResetPasswordEmail(resetPassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/notification")
    public ResponseEntity<Void> sendNotificationMail(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = notificationMapper.dtoToPojo(notificationDTO);
        mailService.sendNotificationEmail(notification);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Void> sendFeedbackMail(@RequestBody FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackMapper.dtoToPojo(feedbackDTO);
        mailService.sendFeedbackEmail(feedback);
        return ResponseEntity.ok().build();
    }

}

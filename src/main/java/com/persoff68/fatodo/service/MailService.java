package com.persoff68.fatodo.service;

import com.persoff68.fatodo.config.AppProperties;
import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.Feedback;
import com.persoff68.fatodo.model.Mail;
import com.persoff68.fatodo.model.MailParams;
import com.persoff68.fatodo.model.Notification;
import com.persoff68.fatodo.model.ResetPassword;
import com.persoff68.fatodo.model.Template;
import com.persoff68.fatodo.service.util.ActivationUtils;
import com.persoff68.fatodo.service.util.FeedbackUtils;
import com.persoff68.fatodo.service.util.MailUtils;
import com.persoff68.fatodo.service.util.NotificationUtils;
import com.persoff68.fatodo.service.util.ResetPasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Year;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final AppProperties appProperties;
    private final TemplateService templateService;
    private final WrapperService wrapperService;
    private final MailSenderService mailSenderService;

    @Value("${mail.adminEmail}")
    private String adminEmail;

    private String baseUrl;

    @PostConstruct
    private void postConstruct() {
        baseUrl = appProperties.getCommon().getBaseUrl();
    }

    public void sendActivationEmail(Activation activation) {
        String language = getLanguage(activation);
        String email = activation.getEmail();
        String username = activation.getUsername();
        UUID code = activation.getCode();

        Template template = templateService.getByCodeAndLanguage("activation", language);
        String subject = template.getSubject();
        String activationLink = ActivationUtils.prepareActivationLink(baseUrl, code);
        String content = ActivationUtils.prepareText(template, username, activationLink);

        log.info("Trying to send an activation mail to {}", email);
        wrapAndSend(language, email, subject, content);
        log.info("Activation mail sent to {}", email);
    }

    public void sendResetPasswordEmail(ResetPassword resetPassword) {
        String language = getLanguage(resetPassword);
        String email = resetPassword.getEmail();
        String username = resetPassword.getUsername();
        UUID code = resetPassword.getCode();

        Template template = templateService.getByCodeAndLanguage("reset-password", language);
        String subject = template.getSubject();
        String resetPasswordLink = ResetPasswordUtils.prepareResetPasswordLink(baseUrl, code);
        String content = ResetPasswordUtils.prepareText(template, username, resetPasswordLink);

        log.info("Trying to send an reset password mail to {}", email);
        wrapAndSend(language, email, subject, content);
        log.info("Reset password mail sent to {}", email);
    }

    public void sendNotificationEmail(Notification notification) {
        String language = getLanguage(notification);
        String email = notification.getEmail();
        String username = notification.getUsername();
        String message = notification.getMessage();
        String url = notification.getUrl();

        Template template = templateService.getByCodeAndLanguage("notification", language);
        String subject = template.getSubject();
        String targetLink = NotificationUtils.prepareTargetLink(baseUrl, url);
        String content = NotificationUtils.prepareText(template, username, message, targetLink);

        wrapAndSend(language, email, subject, content);
    }

    public void sendFeedbackEmail(Feedback feedback) {
        String name = feedback.getName();
        String email = feedback.getEmail();
        String message = feedback.getMessage();

        Template template = templateService.getByCodeAndLanguage("feedback", "EN");
        String subject = template.getSubject();
        String content = FeedbackUtils.prepareText(template, name, email, message);

        wrapAndSend("EN", adminEmail, subject, content);
    }

    private String getLanguage(MailParams mailParams) {
        return mailParams.getLanguage() != null ? mailParams.getLanguage() : "EN";
    }

    private void wrapAndSend(String language, String email, String subject, String content) {
        String wrapper = wrapperService.getWrapperString(language);
        String year = String.valueOf(Year.now().getValue());
        String text = MailUtils.wrapContent(wrapper, content, year);
        Mail mail = Mail.of(email, subject, text);
        mailSenderService.sendMimeMessage(mail);
    }

}

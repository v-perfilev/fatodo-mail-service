package com.persoff68.fatodo.service;

import com.persoff68.fatodo.config.AppProperties;
import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.Mail;
import com.persoff68.fatodo.model.MailParams;
import com.persoff68.fatodo.model.Notification;
import com.persoff68.fatodo.model.ResetPassword;
import com.persoff68.fatodo.model.Template;
import com.persoff68.fatodo.service.util.ActivationUtils;
import com.persoff68.fatodo.service.util.MailUtils;
import com.persoff68.fatodo.service.util.NotificationUtils;
import com.persoff68.fatodo.service.util.ResetPasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final AppProperties appProperties;
    private final TemplateService templateService;
    private final WrapperService wrapperService;
    private final MailSenderService mailSenderService;

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

        wrapAndSend(language, email, subject, content);
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

        wrapAndSend(language, email, subject, content);
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

    private String getLanguage(MailParams mailParams) {
        return mailParams.getLanguage() != null ? mailParams.getLanguage() : "en";
    }

    private void wrapAndSend(String language, String email, String subject, String content) {
        String wrapper = wrapperService.getWrapperString(language);
        String text = MailUtils.wrapContent(wrapper, content);
        Mail mail = Mail.of(email, subject, text);
        mailSenderService.sendMimeMessage(mail);
    }

}

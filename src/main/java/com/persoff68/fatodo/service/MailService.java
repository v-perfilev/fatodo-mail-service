package com.persoff68.fatodo.service;

import com.persoff68.fatodo.config.AppProperties;
import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.ResetPassword;
import com.persoff68.fatodo.model.Template;
import com.persoff68.fatodo.service.exception.MailingFailedException;
import com.persoff68.fatodo.service.util.ActivationUtils;
import com.persoff68.fatodo.service.util.MailUtils;
import com.persoff68.fatodo.service.util.ResetPasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final AppProperties appProperties;
    private final TemplateService templateService;
    private final WrapperService wrapperService;
    private final JavaMailSender mailSender;

    private String baseUrl;

    @PostConstruct
    private void postConstruct() {
        baseUrl = appProperties.getCommon().getBaseUrl();
    }

    public void sendActivationEmail(Activation activation) {
        String language = activation.getLanguage() != null ? activation.getLanguage() : "en";
        String email = activation.getEmail();
        String username = activation.getUsername();

        Template template = templateService.getByCodeAndLanguage("activation", language);
        String subject = template.getSubject();
        String activationLink = ActivationUtils.prepareActivationLink(baseUrl, activation.getCode());
        String content = ActivationUtils.prepareActivationText(template, username, activationLink);

        String wrapper = wrapperService.getWrapperString(language);
        String text = MailUtils.wrapContent(wrapper, content);

        sendMimeMessage(email, subject, text);
    }

    public void sendResetPasswordEmail(ResetPassword resetPassword) {
        String language = resetPassword.getLanguage() != null ? resetPassword.getLanguage() : "en";
        String email = resetPassword.getEmail();
        String username = resetPassword.getUsername();

        Template template = templateService.getByCodeAndLanguage("reset-password", language);
        String subject = template.getSubject();
        String resetPasswordLink = ResetPasswordUtils.prepareLink(baseUrl, resetPassword.getCode());
        String content = ResetPasswordUtils.prepareText(template, username, resetPasswordLink);

        String wrapper = wrapperService.getWrapperString(language);
        String text = MailUtils.wrapContent(wrapper, content);

        sendMimeMessage(email, subject, text);
    }

    private void sendMimeMessage(String email, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new MailingFailedException();
        }
    }

}

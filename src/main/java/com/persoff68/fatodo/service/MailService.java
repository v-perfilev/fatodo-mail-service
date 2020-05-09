package com.persoff68.fatodo.service;

import com.persoff68.fatodo.config.AppProperties;
import com.persoff68.fatodo.config.constant.EmailConstants;
import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.Template;
import com.persoff68.fatodo.service.exception.MailingFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final AppProperties appProperties;
    private final JavaMailSender mailSender;
    private final TemplateService templateService;
    private final WrapperService wrapperService;

    public void sendSimpleEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        mailSender.send(message);
    }

    public void sendActivationEmail(Activation activation) {
        String language = activation.getLanguage() != null ? activation.getLanguage() : "en";
        String email = activation.getEmail();
        String username = activation.getUsername();
        String activationLink = prepareActivationLink(activation.getCode());

        String wrapper = wrapperService.getWrapperString(language);
        Template template = templateService.getByCodeAndLanguage("activation", language);
        String subject = template.getSubject();
        String text = prepareActivationText(wrapper, template, username, activationLink);

        sendMimeMessage(email, subject, text);
    }

    private String prepareActivationLink(String code) {
        String baseUrl = appProperties.getCommon().getBaseUrl();
        String activationRoute = "/activation/";
        return baseUrl + activationRoute + code;
    }

    private String prepareActivationText(String wrapper, Template template, String username, String activationLink) {
        String content = template.getText()
                .replace(EmailConstants.USERNAME_STUB, username)
                .replace(EmailConstants.ACTIVATION_LINK_STUB, activationLink);
        return wrapper.replace(EmailConstants.CONTENT_STUB, content);
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

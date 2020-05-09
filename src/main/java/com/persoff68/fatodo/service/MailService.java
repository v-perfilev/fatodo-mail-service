package com.persoff68.fatodo.service;

import com.persoff68.fatodo.config.AppProperties;
import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.Template;
import com.persoff68.fatodo.service.exception.MailingFailedException;
import com.persoff68.fatodo.service.util.MailUtils;
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
    private final TemplateService templateService;
    private final WrapperService wrapperService;
    private final JavaMailSender mailSender;

    public void sendActivationEmail(Activation activation) {
        String language = activation.getLanguage() != null ? activation.getLanguage() : "en";
        String email = activation.getEmail();
        String username = activation.getUsername();
        String baseUrl = appProperties.getCommon().getBaseUrl();
        String activationLink = MailUtils.prepareActivationLink(baseUrl, activation.getCode());

        String wrapper = wrapperService.getWrapperString(language);
        Template template = templateService.getByCodeAndLanguage("activation", language);
        String subject = template.getSubject();
        String text = MailUtils.prepareActivationText(wrapper, template, username, activationLink);

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

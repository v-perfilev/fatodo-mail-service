package com.persoff68.fatodo.service;

import com.persoff68.fatodo.config.constant.EmailConstants;
import com.persoff68.fatodo.model.Template;
import com.persoff68.fatodo.service.helpers.TemplateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateService templateService;
    private final TemplateHelper templateHelper;

    public void sendSimpleEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        mailSender.send(message);
    }

    public void sendActivationEmail(String to, String language, String activationLink) throws MessagingException {
        Template template = templateService.getByCodeAndLanguage("template.activation", language);
        String subject = template.getSubject();
        String content = template.getText().replace(EmailConstants.ACTIVATION_LINK_STUB, activationLink);
        String text = templateHelper.getTemplateString().replace(EmailConstants.CONTENT_STUB, content);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        mailSender.send(message);
    }

}

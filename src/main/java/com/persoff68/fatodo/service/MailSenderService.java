package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.Mail;
import com.persoff68.fatodo.service.exception.MailingFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${mail.noReplyName}")
    private String noReplyName;

    @Value("${mail.noReplyEmail}")
    private String noReplyEmail;

    @Async
    public void sendMimeMessage(Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(noReplyEmail, noReplyName);
            helper.setTo(mail.getEmail());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getText(), true);
            mailSender.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new MailingFailedException();
        }
    }

}

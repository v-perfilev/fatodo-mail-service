package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.Mail;
import com.persoff68.fatodo.service.exception.MailingFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    public void sendMimeMessage(Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(mail.getEmail());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getText(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new MailingFailedException();
        }
    }

}
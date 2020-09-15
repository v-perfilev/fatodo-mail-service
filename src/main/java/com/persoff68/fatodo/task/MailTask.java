package com.persoff68.fatodo.task;

import com.persoff68.fatodo.model.Mail;
import com.persoff68.fatodo.service.MailSenderService;
import com.persoff68.fatodo.service.MailStoreService;
import com.persoff68.fatodo.service.exception.MailingFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class MailTask {

    private final MailStoreService mailStoreService;
    private final MailSenderService mailSenderService;

    @Scheduled(fixedDelay = 5000)
    public void sendMails() {
        List<Mail> mailList = mailStoreService.getListOfNotSent();
        if (mailList.size() > 0) {
            log.info("Found {} messages to send", mailList.size());
        } else {
            log.info("No messages to send found");
        }
        mailList.forEach(mail -> mailStoreService.changeStatus(mail, Mail.Status.PENDING));
        mailList.forEach(mail -> {
            try {
                mailSenderService.sendMimeMessage(mail);
                mailStoreService.changeStatus(mail, Mail.Status.SENT);
                log.info("Mail sent to {}", mail.getEmail());
            } catch (MailingFailedException e) {
                mailStoreService.changeStatus(mail, Mail.Status.NEW);
                log.error("Mail sending to {} failed", mail.getEmail());
            }
        });
    }

    @Scheduled(cron = "0 * * * * *")
    public void deleteSentMails() {
        mailStoreService.deleteSentMails();
    }

}

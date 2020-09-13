package com.persoff68.fatodo.task;

import com.persoff68.fatodo.FactoryUtils;
import com.persoff68.fatodo.FatodoMailServiceApplication;
import com.persoff68.fatodo.model.Mail;
import com.persoff68.fatodo.repository.MailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FatodoMailServiceApplication.class)
public class MailTaskIT {

    @Autowired
    MailTask mailTask;
    @Autowired
    MailRepository mailRepository;
    @SpyBean
    JavaMailSender mailSender;

    @BeforeEach
    public void setup() {
        when(mailSender.createMimeMessage()).thenReturn(Mockito.mock(MimeMessage.class));
        doNothing().when(mailSender).send(any(MimeMessage.class));
        mailRepository.deleteAll();
        Mail mail1 = FactoryUtils.createMail("1");
        mailRepository.save(mail1);
        Mail mail2 = FactoryUtils.createMail("2");
        mailRepository.save(mail2);
        Mail mail3 = FactoryUtils.createMail("3");
        mail3.setStatus(Mail.Status.SENT);
        mailRepository.save(mail3);
        Mail mail4 = FactoryUtils.createMail("4");
        mail4.setStatus(Mail.Status.SENT);
        mailRepository.save(mail4);
    }

    @Test
    void testSendMails() throws Exception {
        mailTask.sendMails();
        List<Mail> mailList = mailRepository.findAllByStatus(Mail.Status.NEW, PageRequest.of(0, 100));
        assertThat(mailList.size()).isEqualTo(0);
    }

    @Test
    void testDeleteSentMails() throws Exception {
        mailTask.deleteSentMails();
        List<Mail> mailList = mailRepository.findAllByStatus(Mail.Status.SENT, PageRequest.of(0, 100));
        assertThat(mailList.size()).isEqualTo(0);
    }

}

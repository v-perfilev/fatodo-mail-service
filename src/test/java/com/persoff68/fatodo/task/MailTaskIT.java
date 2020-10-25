package com.persoff68.fatodo.task;

import com.persoff68.fatodo.FatodoMailServiceApplication;
import com.persoff68.fatodo.builder.TestMail;
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
        Mail mail1 = TestMail.defaultBuilder().build();
        Mail mail2 = TestMail.defaultBuilder().build();
        Mail mail3 = TestMail.defaultBuilder().status(Mail.Status.SENT).build();
        Mail mail4 = TestMail.defaultBuilder().status(Mail.Status.SENT).build();

        mailRepository.deleteAll();
        mailRepository.save(mail1);
        mailRepository.save(mail2);
        mailRepository.save(mail3);
        mailRepository.save(mail4);

        when(mailSender.createMimeMessage()).thenReturn(Mockito.mock(MimeMessage.class));
        doNothing().when(mailSender).send(any(MimeMessage.class));
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

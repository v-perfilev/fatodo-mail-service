package com.persoff68.fatodo.web.kafka;

import com.persoff68.fatodo.builder.TestActivationDTO;
import com.persoff68.fatodo.builder.TestNotificationDTO;
import com.persoff68.fatodo.builder.TestResetPasswordDTO;
import com.persoff68.fatodo.config.util.KafkaUtils;
import com.persoff68.fatodo.model.dto.ActivationDTO;
import com.persoff68.fatodo.model.dto.NotificationDTO;
import com.persoff68.fatodo.model.dto.ResetPasswordDTO;
import com.persoff68.fatodo.service.MailSenderService;
import com.persoff68.fatodo.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = {
        "kafka.bootstrapAddress=PLAINTEXT://localhost:9092",
        "kafka.groupId=test",
        "kafka.partitions=1",
        "kafka.autoOffsetResetConfig=earliest"
})
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class MailConsumerIT {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private MailConsumer mailConsumer;
    @SpyBean
    private MailService mailService;
    @MockBean
    private MailSenderService mailSenderService;

    private KafkaTemplate<String, ActivationDTO> activationKafkaTemplate;
    private KafkaTemplate<String, ResetPasswordDTO> resetPasswordKafkaTemplate;
    private KafkaTemplate<String, NotificationDTO> notificationKafkaTemplate;

    @BeforeEach
    void setup() {
        activationKafkaTemplate = buildKafkaTemplate();
        resetPasswordKafkaTemplate = buildKafkaTemplate();
        notificationKafkaTemplate = buildKafkaTemplate();

        doNothing().when(mailSenderService).sendMimeMessage(any());
    }

    @Test
    void testSendActivationMail() throws InterruptedException {
        ActivationDTO dto = TestActivationDTO.defaultBuilder().build();
        activationKafkaTemplate.send("mail_auth", "activation", dto);
        boolean messageConsumed = mailConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(mailService, times(1)).sendActivationEmail(any());
    }

    @Test
    void testSendResetPasswordMail() throws InterruptedException {
        ResetPasswordDTO dto = TestResetPasswordDTO.defaultBuilder().build();
        resetPasswordKafkaTemplate.send("mail_auth", "reset-password", dto);
        boolean messageConsumed = mailConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(mailService, times(1)).sendResetPasswordEmail(any());

    }

    @Test
    void testSendNotificationMail() throws InterruptedException {
        NotificationDTO dto = TestNotificationDTO.defaultBuilder().build();
        notificationKafkaTemplate.send("mail_notification", dto);
        boolean messageConsumed = mailConsumer.getLatch().await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        verify(mailService, times(1)).sendNotificationEmail(any());
    }

    private <T> KafkaTemplate<String, T> buildKafkaTemplate() {
        return KafkaUtils.buildJsonKafkaTemplate(embeddedKafkaBroker.getBrokersAsString());
    }

}

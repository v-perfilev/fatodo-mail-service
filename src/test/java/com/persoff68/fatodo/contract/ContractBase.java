package com.persoff68.fatodo.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMessageVerifier
class ContractBase {

    @Autowired
    WebApplicationContext context;

    @SpyBean
    JavaMailSender mailSender;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);
        when(mailSender.createMimeMessage()).thenReturn(Mockito.mock(MimeMessage.class));
        doNothing().when(mailSender).send(any(MimeMessage.class));
    }

}

package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FactoryUtils;
import com.persoff68.fatodo.FatodoMailServiceApplication;
import com.persoff68.fatodo.config.constant.AuthorityType;
import com.persoff68.fatodo.model.dto.ActivationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.internet.MimeMessage;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FatodoMailServiceApplication.class)
public class MailControllerIT {
    private static final String ENDPOINT = "/api/mail";

    @Autowired
    WebApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
    @SpyBean
    JavaMailSender mailSender;

    MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        when(mailSender.createMimeMessage()).thenReturn(Mockito.mock(MimeMessage.class));
        doNothing().when(mailSender).send(any(MimeMessage.class));
    }

    @Test
    @WithMockUser(authorities = AuthorityType.Constants.SYSTEM_VALUE)
    public void testSendActivationMail_ok() throws Exception {
        ActivationDTO dto = FactoryUtils.createActivationDTO("user", UUID.randomUUID().toString(), "en");
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/activation";
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testSendActivationMail_unauthorized() throws Exception {
        ActivationDTO dto = FactoryUtils.createActivationDTO("user", UUID.randomUUID().toString(), "en");
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/activation";
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void testSendActivationMail_forbidden() throws Exception {
        ActivationDTO dto = FactoryUtils.createActivationDTO("user", UUID.randomUUID().toString(), "en");
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/activation";
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isForbidden());
    }


}

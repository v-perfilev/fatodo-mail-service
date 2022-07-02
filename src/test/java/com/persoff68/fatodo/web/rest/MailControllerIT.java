package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FatodoMailServiceApplication;
import com.persoff68.fatodo.annotation.WithCustomSecurityContext;
import com.persoff68.fatodo.builder.TestActivationDTO;
import com.persoff68.fatodo.builder.TestNotificationDTO;
import com.persoff68.fatodo.builder.TestResetPasswordDTO;
import com.persoff68.fatodo.config.constant.AuthorityType;
import com.persoff68.fatodo.model.dto.ActivationDTO;
import com.persoff68.fatodo.model.dto.NotificationDTO;
import com.persoff68.fatodo.model.dto.ResetPasswordDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FatodoMailServiceApplication.class)
@AutoConfigureMockMvc
class MailControllerIT {
    private static final String ENDPOINT = "/api/mails";

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithCustomSecurityContext(authority = AuthorityType.Constants.SYSTEM_VALUE)
    void testSendActivationMail_ok() throws Exception {
        ActivationDTO dto = TestActivationDTO.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/activation";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSendActivationMail_forbidden() throws Exception {
        ActivationDTO dto = TestActivationDTO.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/activation";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void testSendActivationMail_unauthorized() throws Exception {
        ActivationDTO dto = TestActivationDTO.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/activation";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext(authority = AuthorityType.Constants.SYSTEM_VALUE)
    void testSendResetPasswordMail_ok() throws Exception {
        ResetPasswordDTO dto = TestResetPasswordDTO.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/reset-password";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSendResetPasswordMail_forbidden() throws Exception {
        ResetPasswordDTO dto = TestResetPasswordDTO.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/reset-password";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void testSendResetPasswordMail_unauthorized() throws Exception {
        ResetPasswordDTO dto = TestResetPasswordDTO.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/reset-password";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext(authority = AuthorityType.Constants.SYSTEM_VALUE)
    void testSendNotificationMail_ok() throws Exception {
        NotificationDTO dto = TestNotificationDTO.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/notification";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSendNotificationMail_forbidden() throws Exception {
        NotificationDTO dto = TestNotificationDTO.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/notification";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void testSendNotificationMail_unauthorized() throws Exception {
        NotificationDTO dto = TestNotificationDTO.defaultBuilder().build();
        String requestBody = objectMapper.writeValueAsString(dto);
        String url = ENDPOINT + "/notification";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

}

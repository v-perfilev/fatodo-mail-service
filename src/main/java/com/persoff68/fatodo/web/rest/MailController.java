package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.dto.ActivationDTO;
import com.persoff68.fatodo.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping(MailController.ENDPOINT)
public class MailController {
    static final String ENDPOINT = "/api/mail";

    private final MailService mailService;

    @GetMapping("test")
    public ResponseEntity<Void> sendTestEmail() throws MessagingException {
        mailService.sendSimpleEmail("persoff68@gmail.com", "test_subject", "test_text");
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "activation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendTestEmail(@RequestBody ActivationDTO activationDTO) throws MessagingException {
        mailService.sendActivationEmail(activationDTO.getTo(),
                activationDTO.getLanguage(), activationDTO.getActivationLink());
        return ResponseEntity.ok().build();
    }

}

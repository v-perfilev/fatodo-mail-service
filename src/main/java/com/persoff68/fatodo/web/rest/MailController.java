package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.dto.ActivationDTO;
import com.persoff68.fatodo.model.mapper.ActivationMapper;
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
    private final ActivationMapper activationMapper;

    @PostMapping(value = "activation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendActivationEmail(@RequestBody ActivationDTO activationDTO) {
        Activation activation = activationMapper.activationDTOToActivation(activationDTO);
        mailService.sendActivationEmail(activation);
        return ResponseEntity.ok().build();
    }

}

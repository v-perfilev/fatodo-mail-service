package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.ResetPassword;
import com.persoff68.fatodo.model.dto.ActivationDTO;
import com.persoff68.fatodo.model.dto.ResetPasswordDTO;
import com.persoff68.fatodo.model.mapper.ActivationMapper;
import com.persoff68.fatodo.model.mapper.ResetPasswordMapper;
import com.persoff68.fatodo.service.MailBuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(MailController.ENDPOINT)
public class MailController {
    static final String ENDPOINT = "/api/mails";

    private final MailBuildService mailBuildService;
    private final ActivationMapper activationMapper;
    private final ResetPasswordMapper resetPasswordMapper;

    @PostMapping(value = "/activation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendActivationMail(@RequestBody ActivationDTO activationDTO) {
        Activation activation = activationMapper.activationDTOToActivation(activationDTO);
        mailBuildService.addActivationEmail(activation);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendResetPasswordMail(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        ResetPassword resetPassword = resetPasswordMapper.resetPasswordDTOToResetPassword(resetPasswordDTO);
        mailBuildService.addResetPasswordEmail(resetPassword);
        return ResponseEntity.ok().build();
    }

}

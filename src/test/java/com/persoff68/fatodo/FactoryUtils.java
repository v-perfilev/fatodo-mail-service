package com.persoff68.fatodo;

import com.persoff68.fatodo.model.dto.ActivationDTO;
import com.persoff68.fatodo.model.dto.ResetPasswordDTO;

public class FactoryUtils {

    public static ActivationDTO createActivationDTO(String postfix, String code, String language) {
        ActivationDTO dto = new ActivationDTO();
        dto.setEmail("test_" + postfix + "@email.com");
        dto.setUsername("test_" + postfix);
        dto.setCode(code);
        dto.setLanguage(language);
        return dto;
    }

    public static ResetPasswordDTO createResetPasswordDTO(String postfix, String code, String language) {
        ResetPasswordDTO dto = new ResetPasswordDTO();
        dto.setEmail("test_" + postfix + "@email.com");
        dto.setUsername("test_" + postfix);
        dto.setCode(code);
        dto.setLanguage(language);
        return dto;
    }

}

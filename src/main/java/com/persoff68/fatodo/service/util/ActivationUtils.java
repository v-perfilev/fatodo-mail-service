package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.config.constant.EmailConstants;
import com.persoff68.fatodo.model.Template;

import java.util.UUID;

public class ActivationUtils {

    private ActivationUtils() {
    }

    public static String prepareActivationLink(String baseUrl, UUID code) {
        String activationRoute = "/activation/";
        return baseUrl + activationRoute + code;
    }

    public static String prepareActivationText(Template template, String username, String activationLink) {
        return template.getText()
                .replace(EmailConstants.USERNAME_STUB, username)
                .replace(EmailConstants.ACTIVATION_LINK_STUB, activationLink);
    }

}

package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.config.constant.EmailConstants;
import com.persoff68.fatodo.model.Template;

public class MailUtils {

    public static String prepareActivationLink(String baseUrl, String code) {
        String activationRoute = "/activation/";
        return baseUrl + activationRoute + code;
    }

    public static String prepareActivationText(String wrapper, Template template,
                                               String username, String activationLink) {
        String content = template.getText()
                .replace(EmailConstants.USERNAME_STUB, username)
                .replace(EmailConstants.ACTIVATION_LINK_STUB, activationLink);
        return wrapper.replace(EmailConstants.CONTENT_STUB, content);
    }

}

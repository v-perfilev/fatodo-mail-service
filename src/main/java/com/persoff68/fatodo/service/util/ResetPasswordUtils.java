package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.config.constant.EmailConstants;
import com.persoff68.fatodo.model.Template;

public class ResetPasswordUtils {

    private ResetPasswordUtils() {
    }

    public static String prepareLink(String baseUrl, String code) {
        String resetPasswordRoute = "/reset-password/";
        return baseUrl + resetPasswordRoute + code;
    }

    public static String prepareText(Template template, String username, String resetPassword) {
        return template.getText()
                .replace(EmailConstants.USERNAME_STUB, username)
                .replace(EmailConstants.RESET_PASSWORD_LINK_STUB, resetPassword);
    }

}

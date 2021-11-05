package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.config.constant.EmailConstants;
import com.persoff68.fatodo.model.Template;

public class NotificationUtils {

    private NotificationUtils() {
    }

    public static String prepareTargetLink(String baseUrl, String url) {
        return baseUrl + url;
    }

    public static String prepareText(Template template, String username, String message, String targetUrl) {
        return template.getText()
                .replace(EmailConstants.USERNAME_STUB, username)
                .replace(EmailConstants.NOTIFICATION_MESSAGE_STUB, message)
                .replace(EmailConstants.TARGET_LINK_STUB, targetUrl);
    }

}

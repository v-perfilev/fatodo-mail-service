package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.config.constant.EmailConstants;
import com.persoff68.fatodo.model.Template;

public class FeedbackUtils {

    private FeedbackUtils() {
    }

    public static String prepareText(Template template, String name, String email, String message) {
        return template.getText()
                .replace(EmailConstants.NAME_STUB, name)
                .replace(EmailConstants.EMAIL_STUB, email)
                .replace(EmailConstants.MESSAGE_STUB, message);
    }

}

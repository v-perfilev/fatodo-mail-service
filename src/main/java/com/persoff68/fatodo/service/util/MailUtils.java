package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.config.constant.EmailConstants;

public class MailUtils {

    private MailUtils() {
    }

    public static String wrapContent(String wrapper, String content, String year) {
        return wrapper
                .replace(EmailConstants.CONTENT_STUB, content)
                .replace(EmailConstants.YEAR_STUB, year);
    }

}

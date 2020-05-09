package com.persoff68.fatodo.service.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class MailingFailedException extends AbstractException {
    private static final String MESSAGE = "Mail sending failed";
    private static final String FEEDBACK_CODE = "mail.failed";

    public MailingFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE, FEEDBACK_CODE);
    }
}

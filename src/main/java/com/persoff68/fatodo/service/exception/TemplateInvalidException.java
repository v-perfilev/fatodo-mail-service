package com.persoff68.fatodo.service.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class TemplateInvalidException extends AbstractException {
    private static final String MESSAGE = "Template invalid";
    private static final String FEEDBACK_CODE = "mail.template-invalid";

    public TemplateInvalidException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE, FEEDBACK_CODE);
    }
}

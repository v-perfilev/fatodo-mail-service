package com.persoff68.fatodo.service.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class TemplateNotFoundException extends AbstractException {
    private static final String MESSAGE = "Template not found";
    private static final String FEEDBACK_CODE = "mail.templateNotFound";

    public TemplateNotFoundException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE, FEEDBACK_CODE);
    }
}

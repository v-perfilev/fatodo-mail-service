package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.Mail;
import lombok.Builder;

import java.util.UUID;

public class TestMail extends Mail {
    private static final String DEFAULT_VALUE = "test_value";

    @Builder
    public TestMail(UUID id, String email, String subject, String text, Status status) {
        super(email, subject, text, status);
        this.setId(id);
    }

    public static TestMailBuilder defaultBuilder() {
        return TestMail.builder()
                .id(UUID.randomUUID())
                .email(DEFAULT_VALUE + "@email.com")
                .subject(DEFAULT_VALUE)
                .text(DEFAULT_VALUE)
                .status(Status.NEW);
    }

}

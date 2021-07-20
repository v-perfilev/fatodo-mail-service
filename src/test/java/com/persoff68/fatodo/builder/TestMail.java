package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.Mail;
import lombok.Builder;

public class TestMail extends Mail {
    private static final String DEFAULT_VALUE = "test_value";

    @Builder
    public TestMail(String email, String subject, String text) {
        super(email, subject, text);
    }

    public static TestMailBuilder defaultBuilder() {
        return TestMail.builder()
                .email(DEFAULT_VALUE + "@email.com")
                .subject(DEFAULT_VALUE)
                .text(DEFAULT_VALUE);
    }

}

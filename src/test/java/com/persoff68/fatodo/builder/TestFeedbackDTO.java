package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.dto.FeedbackDTO;
import lombok.Builder;

import javax.validation.constraints.NotNull;

public class TestFeedbackDTO extends FeedbackDTO {
    private static final String DEFAULT_VALUE = "test_value";

    @Builder
    TestFeedbackDTO(@NotNull String name, @NotNull String email, @NotNull String message) {
        super(name, email, message);
    }

    public static TestFeedbackDTOBuilder defaultBuilder() {
        return TestFeedbackDTO.builder()
                .name(DEFAULT_VALUE)
                .email(DEFAULT_VALUE + "@email.com")
                .message(DEFAULT_VALUE);
    }
}

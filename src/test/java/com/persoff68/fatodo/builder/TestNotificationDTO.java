
package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.dto.NotificationDTO;
import lombok.Builder;

import javax.validation.constraints.NotNull;

public class TestNotificationDTO extends NotificationDTO {
    private static final String DEFAULT_VALUE = "test_value";

    @Builder
    public TestNotificationDTO(String language,
                               @NotNull String email,
                               @NotNull String username,
                               @NotNull String message,
                               @NotNull String url) {
        super(language, email, username, message, url);
    }

    public static TestNotificationDTOBuilder defaultBuilder() {
        return TestNotificationDTO.builder()
                .language("en")
                .email(DEFAULT_VALUE + "@email.com")
                .username(DEFAULT_VALUE)
                .message(DEFAULT_VALUE)
                .url(DEFAULT_VALUE);
    }
}

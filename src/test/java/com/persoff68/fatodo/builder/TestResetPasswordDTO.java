package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.dto.ResetPasswordDTO;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class TestResetPasswordDTO extends ResetPasswordDTO {
    private static final String DEFAULT_VALUE = "test_value";

    @Builder
    public TestResetPasswordDTO(String language, @NotNull String email, @NotNull String username, @NotNull UUID code) {
        super(language, email, username, code);
    }

    public static TestResetPasswordDTOBuilder defaultBuilder() {
        return TestResetPasswordDTO.builder()
                .language("en")
                .email(DEFAULT_VALUE + "@email.com")
                .username(DEFAULT_VALUE)
                .code(UUID.randomUUID());
    }
}

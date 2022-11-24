package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.dto.ActivationDTO;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class TestActivationDTO extends ActivationDTO {
    private static final String DEFAULT_VALUE = "test_value";

    @Builder
    TestActivationDTO(String language, @NotNull String email, @NotNull String username, @NotNull UUID code) {
        super(language, email, username, code);
    }

    public static TestActivationDTOBuilder defaultBuilder() {
        return TestActivationDTO.builder()
                .language("EN")
                .email(DEFAULT_VALUE + "@email.com")
                .username(DEFAULT_VALUE)
                .code(UUID.randomUUID());
    }
}

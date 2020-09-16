package com.persoff68.fatodo.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ActivationDTO implements AbstractDTO {
    private String language;
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String code;
}

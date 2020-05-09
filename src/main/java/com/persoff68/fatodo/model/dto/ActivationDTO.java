package com.persoff68.fatodo.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ActivationDTO extends AbstractDTO {
    private String language;
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String code;
}

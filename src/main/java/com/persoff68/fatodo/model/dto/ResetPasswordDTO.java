package com.persoff68.fatodo.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class ResetPasswordDTO implements Serializable {
    private String language;
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String code;
}

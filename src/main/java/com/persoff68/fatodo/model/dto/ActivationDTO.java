package com.persoff68.fatodo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivationDTO {

    private String language;

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private UUID code;

}

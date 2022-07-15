package com.persoff68.fatodo.model.dto;

import com.persoff68.fatodo.config.constant.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = AppConstants.SERIAL_VERSION_UID;

    private String language;
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private UUID code;
}

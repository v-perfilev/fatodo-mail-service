package com.persoff68.fatodo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO implements Serializable {

    private String language;
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String message;
    @NotNull
    private String url;

}

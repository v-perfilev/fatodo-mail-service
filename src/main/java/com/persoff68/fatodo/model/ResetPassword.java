package com.persoff68.fatodo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ResetPassword implements Serializable {

    private String language;
    private String email;
    private String username;
    private UUID code;

}

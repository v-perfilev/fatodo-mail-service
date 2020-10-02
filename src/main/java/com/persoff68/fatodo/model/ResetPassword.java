package com.persoff68.fatodo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResetPassword implements Serializable {

    private String language;
    private String email;
    private String username;
    private String code;

}

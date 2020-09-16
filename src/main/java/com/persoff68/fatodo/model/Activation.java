package com.persoff68.fatodo.model;

import lombok.Data;

@Data
public class Activation implements AbstractModel {

    private String language;
    private String email;
    private String username;
    private String code;

}

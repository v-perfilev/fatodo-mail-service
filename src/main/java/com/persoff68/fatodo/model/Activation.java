package com.persoff68.fatodo.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Activation {

    private String language;
    private String email;
    private String username;
    private UUID code;

}

package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Activation extends AbstractModel {

    private String language;
    private String email;
    private String username;
    private String code;

}

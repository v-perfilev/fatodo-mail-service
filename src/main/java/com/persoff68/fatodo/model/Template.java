package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Template implements AbstractModel {

    private String code;

    private String language;

    private String subject;

    private String text;

}

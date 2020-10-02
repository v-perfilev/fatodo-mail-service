package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Template {

    private String code;
    private String language;
    private String subject;
    private String text;

}

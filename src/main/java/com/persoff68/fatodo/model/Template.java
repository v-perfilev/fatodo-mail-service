package com.persoff68.fatodo.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
public class Template extends AbstractModel {

    private String code;

    private String language;

    private String subject;

    private String text;

}

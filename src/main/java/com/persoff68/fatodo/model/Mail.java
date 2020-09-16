package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ftd_mail")
@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class Mail extends AbstractAuditingModel {

    private final String email;

    private final String subject;

    private final String text;

    private Status status = Status.NEW;

    public enum Status {
        NEW,
        PENDING,
        SENT
    }
}

package com.persoff68.fatodo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ftd_mail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Mail extends AbstractAuditingModel {

    private String email;

    private String subject;

    private String text;

    private Status status = Status.NEW;

    public static Mail of(String email, String subject, String text) {
        Mail mail = new Mail();
        mail.setEmail(email);
        mail.setSubject(subject);
        mail.setText(text);
        return mail;
    }

    public enum Status {
        NEW,
        PENDING,
        SENT
    }
}

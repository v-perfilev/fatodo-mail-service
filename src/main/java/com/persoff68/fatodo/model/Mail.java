package com.persoff68.fatodo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private String email;

    private String subject;

    private String text;

    public static Mail of(String email, String subject, String text) {
        Mail mail = new Mail();
        mail.setEmail(email);
        mail.setSubject(subject);
        mail.setText(text);
        return mail;
    }

}

package com.persoff68.fatodo.model;

import lombok.Data;

@Data
public class Notification implements MailParams {

    private String language;
    private String email;
    private String username;
    private String message;
    private String url;

}

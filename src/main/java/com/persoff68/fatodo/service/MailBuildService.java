package com.persoff68.fatodo.service;

import com.persoff68.fatodo.config.AppProperties;
import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.Mail;
import com.persoff68.fatodo.model.ResetPassword;
import com.persoff68.fatodo.model.Template;
import com.persoff68.fatodo.service.util.ActivationUtils;
import com.persoff68.fatodo.service.util.MailUtils;
import com.persoff68.fatodo.service.util.ResetPasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MailBuildService {

    private final AppProperties appProperties;
    private final TemplateService templateService;
    private final WrapperService wrapperService;
    private final MailStoreService mailStoreService;

    private String baseUrl;

    @PostConstruct
    private void postConstruct() {
        baseUrl = appProperties.getCommon().getBaseUrl();
    }

    public void addActivationEmail(Activation activation) {
        String language = activation.getLanguage() != null ? activation.getLanguage() : "en";
        String email = activation.getEmail();
        String username = activation.getUsername();

        Template template = templateService.getByCodeAndLanguage("activation", language);
        String subject = template.getSubject();
        String activationLink = ActivationUtils.prepareActivationLink(baseUrl, activation.getCode());
        String content = ActivationUtils.prepareActivationText(template, username, activationLink);

        String wrapper = wrapperService.getWrapperString(language);
        String text = MailUtils.wrapContent(wrapper, content);

        Mail mail = Mail.of(email, subject, text);
        mailStoreService.add(mail);
    }

    public void addResetPasswordEmail(ResetPassword resetPassword) {
        String language = resetPassword.getLanguage() != null ? resetPassword.getLanguage() : "en";
        String email = resetPassword.getEmail();
        String username = resetPassword.getUsername();

        Template template = templateService.getByCodeAndLanguage("reset-password", language);
        String subject = template.getSubject();
        String resetPasswordLink = ResetPasswordUtils.prepareLink(baseUrl, resetPassword.getCode());
        String content = ResetPasswordUtils.prepareText(template, username, resetPasswordLink);

        String wrapper = wrapperService.getWrapperString(language);
        String text = MailUtils.wrapContent(wrapper, content);

        Mail mail = Mail.of(email, subject, text);
        mailStoreService.add(mail);
    }

}

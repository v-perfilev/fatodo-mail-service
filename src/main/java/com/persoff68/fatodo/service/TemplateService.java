package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.Template;
import com.persoff68.fatodo.service.exception.TemplateInvalidException;
import com.persoff68.fatodo.service.exception.TemplateNotFoundException;
import com.persoff68.fatodo.service.helper.ResourceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private static final String TEMPLATE_PATH = "classpath:templates/";

    private final ResourceHelper resourceHelper;

    public Template getByCodeAndLanguage(String code, String language) {
        code = code.replace(".", "/");
        String subject = getSubject(code, language);
        String text = getText(code, language);

        Template template = new Template();
        template.setCode(code);
        template.setLanguage(language);
        template.setSubject(subject);
        template.setText(text);

        return template;
    }

    private String getSubject(String code, String language) {
        String subjectPath = TEMPLATE_PATH + code + File.separator + "subjects";
        List<String> subjectList = resourceHelper.loadResource(subjectPath);
        if (subjectList.isEmpty()) {
            throw new TemplateNotFoundException();
        }
        String subjectWithLanguage = subjectList.stream()
                .filter(s -> s.startsWith(language))
                .findFirst()
                .orElseThrow(TemplateInvalidException::new);
        return subjectWithLanguage.substring(language.length()).trim();
    }

    private String getText(String code, String language) {
        String templatePath = TEMPLATE_PATH + code + File.separator + language + ".html";
        List<String> templateList = resourceHelper.loadResource(templatePath);
        if (templateList.isEmpty()) {
            throw new TemplateNotFoundException();
        }
        return String.join("", templateList);
    }

}

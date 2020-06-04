package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.Template;
import com.persoff68.fatodo.service.exception.TemplateInvalidException;
import com.persoff68.fatodo.service.exception.TemplateNotFoundException;
import com.persoff68.fatodo.service.helper.ResourceHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private static final String TEMPLATE_PATH = "classpath:templates/";

    private final ResourceHelper resourceHelper;

    public Template getByCodeAndLanguage(String code, String language) {
        try {
            code = code.replace(".", "/");
            String subject = getSubject(code, language);
            String text = getText(code, language);

            Template template = new Template();
            template.setCode(code);
            template.setLanguage(language);
            template.setSubject(subject);
            template.setText(text);

            return template;
        } catch (IOException e) {
            throw new TemplateInvalidException();
        }
    }

    private String getSubject(String code, String language) throws IOException {
        String subjectPath = TEMPLATE_PATH + code + File.separator + "subjects";
        File subjectFile = resourceHelper.loadResource(subjectPath);
        if (subjectFile == null) {
            throw new TemplateNotFoundException();
        }
        List<String> subjectList = FileUtils.readLines(subjectFile);
        String subjectWithLanguage = subjectList.stream()
                .filter(s -> s.startsWith(language))
                .findFirst()
                .orElseThrow(TemplateInvalidException::new);
        return subjectWithLanguage.substring(language.length()).trim();
    }

    private String getText(String code, String language) throws IOException {
        String templatePath = TEMPLATE_PATH + code + File.separator + language + ".html";
        File templateFile = resourceHelper.loadResource(templatePath);
        if (templateFile == null) {
            throw new TemplateNotFoundException();
        }
        return FileUtils.readFileToString(templateFile);
    }

}

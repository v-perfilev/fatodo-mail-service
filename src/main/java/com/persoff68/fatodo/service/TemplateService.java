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

    private final ResourceHelper resourceHelper;

    public Template getByCodeAndLanguage(String code, String language) {
        try {
            code = code.replace(".", "/");
            String path = "classpath:templates/" + code + "/" + language + ".tem";
            File file = resourceHelper.loadResource(path);
            List<String> stringList = FileUtils.readLines(file);

            checkStringList(stringList);
            String subject = getSubjectFromList(stringList);
            String text = getTextFromList(stringList);

            Template template = new Template();
            template.setCode(code);
            template.setLanguage(language);
            template.setSubject(subject);
            template.setText(text);

            return template;
        } catch (IOException e) {
            throw new TemplateNotFoundException();
        }
    }

    private void checkStringList(List<String> stringList) {
        if (stringList.size() < 2) {
            throw new TemplateInvalidException();
        }
    }

    private String getSubjectFromList(List<String> stringList) {
        return stringList.get(0);
    }

    private String getTextFromList(List<String> stringList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < stringList.size(); i++) {
            sb.append(stringList.get(i));
        }
        return sb.toString();
    }

}

package com.persoff68.fatodo.service;

import com.persoff68.fatodo.service.exception.TemplateNotFoundException;
import com.persoff68.fatodo.service.helper.ResourceHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class WrapperService {
    private static final String TEMPLATE_PATH = "classpath:templates/wrapper/";

    private final ResourceHelper resourceHelper;

    public String getWrapperString(String language) {
        try {
            File file = resourceHelper.loadResource(TEMPLATE_PATH + language + ".html");
            StringBuilder sb = new StringBuilder();
            FileUtils.readLines(file)
                    .forEach(sb::append);
            return sb.toString();
        } catch (IOException e) {
            throw new TemplateNotFoundException();
        }
    }
}

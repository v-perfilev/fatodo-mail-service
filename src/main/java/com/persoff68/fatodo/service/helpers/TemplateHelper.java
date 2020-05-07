package com.persoff68.fatodo.service.helpers;

import com.google.common.io.Files;
import com.persoff68.fatodo.config.constant.EmailConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Component
@RequiredArgsConstructor
public class TemplateHelper {
    private final static String TEMPlATE_PATH = "classpath:templates/template.html";

    private final ResourceHelper resourceHelper;

    public String getTemplateString() {
        try {
            File file = resourceHelper.loadResource(TEMPlATE_PATH);
            StringBuilder sb = new StringBuilder();
            Files.readLines(file, Charset.defaultCharset())
                    .forEach(sb::append);
            return sb.toString();
        } catch (IOException e) {
            return EmailConstants.CONTENT_STUB;
        }
    }
}

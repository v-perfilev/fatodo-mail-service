package com.persoff68.fatodo.service.helpers;

import com.persoff68.fatodo.config.constant.EmailConstants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class WrapperHelper {
    private final static String TEMPlATE_PATH = "classpath:templates/template.html";

    private final ResourceHelper resourceHelper;

    public String getWrapperString() {
        try {
            File file = resourceHelper.loadResource(TEMPlATE_PATH);
            StringBuilder sb = new StringBuilder();
            FileUtils.readLines(file)
                    .forEach(sb::append);
            return sb.toString();
        } catch (IOException e) {
            return EmailConstants.CONTENT_STUB;
        }
    }
}

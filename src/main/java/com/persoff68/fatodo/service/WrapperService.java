package com.persoff68.fatodo.service;

import com.persoff68.fatodo.service.exception.TemplateNotFoundException;
import com.persoff68.fatodo.service.helper.ResourceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WrapperService {
    private static final String TEMPLATE_PATH = "classpath:templates/wrapper/";

    private final ResourceHelper resourceHelper;

    public String getWrapperString(String language) {
        List<String> stringList = resourceHelper.loadResource(TEMPLATE_PATH + language + ".html");
        if (stringList == null) {
            throw new TemplateNotFoundException();
        }
        return String.join("", stringList);
    }
}

package com.persoff68.fatodo.service;

import com.persoff68.fatodo.FatodoMailServiceApplication;
import com.persoff68.fatodo.model.Template;
import com.persoff68.fatodo.service.exception.TemplateNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = FatodoMailServiceApplication.class)
class TemplateServiceTest {

    private final static String[] CODES = {"activation", "reset-password"};
    private final static String[] LANGUAGES = {"en", "ru"};

    @Autowired
    TemplateService templateService;

    @Test
    void testGetByCodeAndLanguage() {
        List<Template> templateList = new ArrayList<>();
        for (String language : LANGUAGES) {
            for (String code : CODES) {
                Template template = templateService.getByCodeAndLanguage(code, language);
                templateList.add(template);
            }
        }

        for (Template template : templateList) {
            assertThat(template.getText()).isNotEmpty();
        }
    }

    @Test
    void testGetByCodeAndLanguage_notFound() {
        assertThatThrownBy(() -> templateService.getByCodeAndLanguage("notExists", "mars"))
                .isInstanceOf(TemplateNotFoundException.class);
    }

}

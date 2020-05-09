package com.persoff68.fatodo.service;

import com.persoff68.fatodo.FatodoMailServiceApplication;
import com.persoff68.fatodo.service.exception.TemplateNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = FatodoMailServiceApplication.class)
public class WrapperServiceTest {

    private final static String[] LANGUAGES = {"en", "ru"};

    @Autowired
    WrapperService wrapperService;

    @MockBean
    JavaMailSender javaMailSender;

    @Test
    void testGetWrapperString() {
        List<String> wrapperStringList = new ArrayList<>();
        for (String language : LANGUAGES) {
            String wrapperString = wrapperService.getWrapperString(language);
            wrapperStringList.add(wrapperString);
        }

        for (String string : wrapperStringList) {
            assertThat(string).isNotEmpty();
        }
    }

    @Test
    void testGetByCodeAndLanguage_notFound() {
        assertThatThrownBy(() -> wrapperService.getWrapperString("mars"))
                .isInstanceOf(TemplateNotFoundException.class);
    }

}

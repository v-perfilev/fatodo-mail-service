package com.persoff68.fatodo.service.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResourceHelper {

    private final ResourceLoader resourceLoader;

    public List<String> loadResource(String path) {
        try {
            Resource resource = resourceLoader.getResource(path);
            InputStream inputStream = resource.getInputStream();
            return IOUtils.readLines(inputStream);
        } catch (IOException e) {
            return null;
        }
    }

}

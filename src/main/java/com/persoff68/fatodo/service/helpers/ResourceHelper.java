package com.persoff68.fatodo.service.helpers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ResourceHelper {

    private final ResourceLoader resourceLoader;

    public List<File> loadResources(String pattern) throws IOException {
        Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
        List<File> fileList = new ArrayList<>();
        for (Resource resource : resources) {
            fileList.add(resource.getFile());
        }
        return fileList;
    }

    public File loadResource(String path) throws IOException {
        return ResourceUtils.getFile(path);
    }

}

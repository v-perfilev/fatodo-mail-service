package com.persoff68.fatodo.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ResourceHelper {

    public File loadResource(String path) {
        try {
            return ResourceUtils.getFile(path);
        } catch (IOException e) {
            return null;
        }
    }

}

package com.veyyon.at.common.util;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UrlUtil {
    public String buildUrl(String inEnv, String inPath, String inDB) {
        return inEnv+ File.pathSeparator+inEnv;
    }
}

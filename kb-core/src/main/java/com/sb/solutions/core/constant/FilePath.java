package com.sb.solutions.core.constant;

import org.springframework.stereotype.Component;

/**
 * @author Rujan Maharjan on 4/3/2019
 */
@Component
public class FilePath {
    public static String getOSPath() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("win") >= 0) {
            return UploadDir.WINDOWS_PATH;
        } else {
            return UploadDir.Linux_PATH;
        }
    }
}

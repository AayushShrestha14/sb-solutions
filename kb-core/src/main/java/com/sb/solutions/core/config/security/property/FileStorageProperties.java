package com.sb.solutions.core.config.security.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "file", ignoreUnknownFields = false)
@Data
public class FileStorageProperties {

    private String uploadDirectory;

}

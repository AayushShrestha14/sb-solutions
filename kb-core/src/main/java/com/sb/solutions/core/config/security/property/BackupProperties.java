package com.sb.solutions.core.config.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import javax.validation.constraints.NotNull;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "backup", ignoreUnknownFields = false)
@Data
public class BackupProperties {

    @NotNull
    private int rollBack;

    @NotNull
    private int threadPool;
}

package com.sb.solutions.core.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.graalvm.compiler.core.common.SuppressFBWarnings;

@Data
@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" }, justification = "I prefer to suppress these FindBugs warnings")
public abstract class BaseDto<PK> {

    private PK id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastModifiedAt = new Date();

    private Long createdBy;

    private Long modifiedBy;

    private int version;
}

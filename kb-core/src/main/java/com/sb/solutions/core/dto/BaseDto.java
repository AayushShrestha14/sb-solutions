package com.sb.solutions.core.dto;

import java.util.Date;

import lombok.Data;

@Data
public abstract class BaseDto<PK> {

    private PK id;

    private Date createdAt = new Date();

    private Date lastModifiedAt;

    private int version;

    public boolean isNew() {
        return null == id;
    }
}

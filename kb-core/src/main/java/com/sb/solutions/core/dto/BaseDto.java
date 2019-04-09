package com.sb.solutions.core.dto;

import java.util.Date;

import lombok.Data;

@Data
public abstract class BaseDto {

    private long id;

    private Date createdAt;

    private Date lastModifiedAt;

    private int version;
}

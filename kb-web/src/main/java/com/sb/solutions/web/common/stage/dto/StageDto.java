package com.sb.solutions.web.common.stage.dto;

import com.sb.solutions.core.enums.DocAction;
import lombok.Data;

/**
 * @author Sunil Babu Shrestha on 5/26/2019
 */
@Data
public class StageDto {

    private long fromUser;

    private long toUser;

    private DocAction docAction;

    private String comment;
}

package com.sb.solutions.web.memo.v1.dto;

import com.sb.solutions.api.memo.enums.Stage;
import com.sb.solutions.core.dto.BaseDto;

public class MemoStageDto extends BaseDto {

    private MemoDto memo;

    private MemoUserDto sentBy;

    private MemoUserDto sentTo;

    private Stage stage;

    private String note;
}

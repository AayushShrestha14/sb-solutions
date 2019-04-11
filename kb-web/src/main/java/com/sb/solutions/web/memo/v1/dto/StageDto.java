package com.sb.solutions.web.memo.v1.dto;

import com.sb.solutions.api.memo.enums.Stage;
import com.sb.solutions.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StageDto extends BaseDto<Long> {

    private MemoUserDto sentBy;

    private MemoUserDto sentTo;

    private Stage stage;

    private String note;
}

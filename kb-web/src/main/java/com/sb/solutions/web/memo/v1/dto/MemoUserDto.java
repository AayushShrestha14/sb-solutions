package com.sb.solutions.web.memo.v1.dto;

import com.sb.solutions.core.dto.BaseDto;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemoUserDto extends BaseDto<Long> {

    private String name;

    private Status status;

    private int version;
}

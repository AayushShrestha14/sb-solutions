package com.sb.solutions.web.memo.v1.dto;

import com.sb.solutions.core.dto.BaseDto;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoUserDto extends BaseDto {

    private String name;

    private Status status;

    private int version;
}

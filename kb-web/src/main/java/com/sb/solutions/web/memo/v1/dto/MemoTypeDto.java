package com.sb.solutions.web.memo.v1.dto;

import javax.validation.constraints.NotNull;

import com.sb.solutions.core.dto.BaseDto;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoTypeDto extends BaseDto {

    @NotNull
    private String name;

    @NotNull
    private Status status = Status.ACTIVE;
}

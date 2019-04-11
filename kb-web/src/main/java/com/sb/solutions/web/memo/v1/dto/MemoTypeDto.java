package com.sb.solutions.web.memo.v1.dto;

import javax.validation.constraints.NotNull;

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
public class MemoTypeDto extends BaseDto<Long> {

    @NotNull
    private String name;

    @NotNull
    private Status status = Status.ACTIVE;
}

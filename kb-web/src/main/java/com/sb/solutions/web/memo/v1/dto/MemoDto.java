package com.sb.solutions.web.memo.v1.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;

import com.sb.solutions.api.memo.enums.Stage;
import com.sb.solutions.core.dto.BaseDto;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoDto extends BaseDto {

    @NotNull
    private String subject;

    @NotNull
    private String content;

    private MemoUserDto sentBy;

    private MemoUserDto sentTo;

    private Set<MemoUserDto> cc;

    private Set<MemoUserDto> bcc;

    private Status status;

    private Stage stage;

    private MemoTypeDto type;

    private Set<StageDto> stages = new HashSet<>();

    public void addStage(StageDto s) {
        stages.add(s);
    }
}

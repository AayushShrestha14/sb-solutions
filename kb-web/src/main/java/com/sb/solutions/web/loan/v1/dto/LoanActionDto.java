package com.sb.solutions.web.loan.v1.dto;

import com.sb.solutions.core.dto.BaseDto;
import com.sb.solutions.core.enums.DocAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoanActionDto extends BaseDto<Long> {

    private Long customerId;

    @NotNull
    private Long LoanConfigId;

    @NotNull
    private DocAction docAction;

    private Long sendBy;


    private Long toUser;

    private Long toRole;

    private String comment;
}
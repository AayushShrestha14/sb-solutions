package com.sb.solutions.web.common.stage.dto;

import com.sb.solutions.core.dto.BaseDto;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.web.user.dto.RoleDto;
import com.sb.solutions.web.user.dto.UserDto;
import lombok.Data;

/**
 * @author Sunil Babu Shrestha on 5/26/2019
 */
@Data
public class StageDto extends BaseDto<Long> {

    private Long customerLoanId;

    private Long LoanConfigId;

    private DocAction docAction;

    private UserDto fromUser;

    private RoleDto fromRole;

    private UserDto toUser;

    private RoleDto toRole;


    private String comment;
}

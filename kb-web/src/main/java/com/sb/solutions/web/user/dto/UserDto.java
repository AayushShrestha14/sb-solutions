package com.sb.solutions.web.user.dto;

import com.sb.solutions.core.dto.BaseDto;
import com.sb.solutions.web.branch.v1.dto.BranchDto;
import lombok.Data;

import java.util.List;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Data

public class UserDto extends BaseDto<Long> {

    private List<BranchDto> branch;
    private RoleDto role;

}

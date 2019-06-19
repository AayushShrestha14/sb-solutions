package com.sb.solutions.web.user.dto;

import com.sb.solutions.core.dto.BaseDto;
import lombok.Data;

/**
 * @author Rujan Maharjan on 6/12/2019
 */

@Data
public class RoleDto extends BaseDto<Long> {

    private String roleName;
}

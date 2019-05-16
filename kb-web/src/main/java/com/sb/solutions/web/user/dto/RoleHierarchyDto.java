package com.sb.solutions.web.user.dto;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rujan Maharjan on 5/13/2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleHierarchyDto extends BaseDto<Long> {

    private Role role;
    private Long roleOrder;



}

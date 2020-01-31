package com.sb.solutions.web.user.approval;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.utils.ApprovalType;
import com.sb.solutions.api.authorization.entity.Role;
import com.sb.solutions.core.dto.BaseDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApprovalRoleHierarchyDto extends BaseDto<Long> {

    private Role role;

    private Long roleOrder;

    private ApprovalType approvalType;

    private Long refId;
}

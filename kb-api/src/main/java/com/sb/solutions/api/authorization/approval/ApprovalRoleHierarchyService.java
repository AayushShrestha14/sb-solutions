package com.sb.solutions.api.authorization.approval;

import java.util.List;

import com.sb.solutions.core.service.BaseService;

public interface ApprovalRoleHierarchyService extends BaseService<ApprovalRoleHierarchy> {

    List<ApprovalRoleHierarchy> getForwardRolesForRole(Long roleId,
        ApprovalType approvalType, Long typeId,
        ApprovalType refType, Long refId);

    List<ApprovalRoleHierarchy> getBackwardRolesForRole(Long roleId,
        ApprovalType approvalType, Long typeId,
        ApprovalType refType, Long refId);

    List<ApprovalRoleHierarchy> getRoles(ApprovalType approvalType, Long refId);

    List<ApprovalRoleHierarchy> getDefaultRoleHierarchies(ApprovalType approvalType, Long refId);
}

package com.sb.solutions.api.authorization.approval;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sb.solutions.core.utils.ApprovalType;

@Repository
public interface ApprovalRoleHierarchyRepository extends
    JpaRepository<ApprovalRoleHierarchy, Long> {

    List<ApprovalRoleHierarchy> findAllByApprovalTypeEqualsAndRefId(
        ApprovalType approvalType,
        Long refId);
}

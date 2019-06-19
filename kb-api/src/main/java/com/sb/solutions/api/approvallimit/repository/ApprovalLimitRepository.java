package com.sb.solutions.api.approvallimit.repository;


import com.sb.solutions.api.approvallimit.entity.ApprovalLimit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApprovalLimitRepository extends JpaRepository<ApprovalLimit, Long> {

    @Query(value = "select a from ApprovalLimit a")
    Page<ApprovalLimit> approvalLimitFilter(Pageable pageable);

    ApprovalLimit  getByAuthoritiesIdAndLoanCategoryId(Long id,Long loanConfigId);

}

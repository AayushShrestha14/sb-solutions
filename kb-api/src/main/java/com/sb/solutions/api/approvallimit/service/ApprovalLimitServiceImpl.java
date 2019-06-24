package com.sb.solutions.api.approvallimit.service;

import com.sb.solutions.api.approvallimit.emuns.LoanApprovalType;
import com.sb.solutions.api.approvallimit.entity.ApprovalLimit;
import com.sb.solutions.api.approvallimit.repository.ApprovalLimitRepository;
import com.sb.solutions.core.dto.SearchDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalLimitServiceImpl implements ApprovalLimitService {

    private final ApprovalLimitRepository approvalLimitRepository;

    public ApprovalLimitServiceImpl(
            @Autowired ApprovalLimitRepository approvalLimitRepository) {
        this.approvalLimitRepository = approvalLimitRepository;
    }

    @Override
    public List<ApprovalLimit> findAll() {
        return approvalLimitRepository.findAll();
    }

    @Override
    public ApprovalLimit findOne(Long id) {
        return null;
    }

    @Override
    public ApprovalLimit save(ApprovalLimit approvalLimit) {
        ApprovalLimit approvalLimit1 = approvalLimitRepository
                .getByAuthoritiesIdAndLoanCategoryIdAndLoanApprovalType(
                        approvalLimit.getAuthorities().getId(),
                        approvalLimit.getLoanCategory().getId(),
                        approvalLimit.getLoanApprovalType());
        if (approvalLimit1 != null) {
            throw new ConstraintViolationException("Already Exist", null, "Approval Limit Already Exist");
        }
        return approvalLimitRepository.save(approvalLimit);


    }

    @Override
    public Page<ApprovalLimit> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object, SearchDto.class);
        return approvalLimitRepository.approvalLimitFilter(pageable);
    }

    @Override
    public ApprovalLimit getByRoleAndLoan(Long roleId, Long loanConfigId, LoanApprovalType loanApprovalType) {
        return approvalLimitRepository.getByAuthoritiesIdAndLoanCategoryIdAndLoanApprovalType(roleId, loanConfigId, loanApprovalType);
    }
}

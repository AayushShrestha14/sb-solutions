package com.sb.solutions.api.approvallimit.service;

import com.sb.solutions.api.approvallimit.entity.ApprovalLimit;
import com.sb.solutions.api.approvallimit.repository.ApprovalLimitRepository;
import com.sb.solutions.core.dto.SearchDto;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ApprovalLimitServiceImpl implements ApprovalLimitService {

    private final ApprovalLimitRepository approvalLimitRepository;

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
        approvalLimit.setLastModifiedAt(new Date());
        return approvalLimitRepository.save(approvalLimit);

    }

    @Override
    public Page<ApprovalLimit> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object, SearchDto.class);
        return approvalLimitRepository.approvalLimitFilter(pageable);
    }

    @Override
    public ApprovalLimit getByRoleAndLoan(Long roleId, Long loanConfigId) {
        return approvalLimitRepository.getByAuthoritiesIdAndLoanCategoryId(roleId,loanConfigId);
    }
}

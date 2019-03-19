package com.sb.solutions.api.approvallimit.service;

import com.sb.solutions.api.approvallimit.entity.ApprovalLimit;
import com.sb.solutions.api.approvallimit.repository.ApprovalLimitRepository;
import lombok.AllArgsConstructor;
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
        approvalLimit.setLastModified(new Date());
        return approvalLimitRepository.save(approvalLimit);

    }

    @Override
    public Page<ApprovalLimit> findAllPageable(Object approvalLimit, Pageable pageable) {

        return approvalLimitRepository.approvalLimitFilter(pageable);
    }

}

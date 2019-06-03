package com.sb.solutions.api.openingForm.service;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.core.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OpeningFormService extends BaseService<OpeningForm> {
    Page<OpeningForm> findAllByBranchAndAccountStatus(Branch branch, Pageable pageable, String accountStatus);
    Map<Object, Object> getStatus(Long branchId);
    OpeningForm updateOpeningCustomer(Long id, String status);
}

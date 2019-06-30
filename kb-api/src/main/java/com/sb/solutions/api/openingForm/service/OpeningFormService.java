package com.sb.solutions.api.openingForm.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.core.service.BaseService;

public interface OpeningFormService extends BaseService<OpeningForm> {

    Page<OpeningForm> findAllByBranchAndAccountStatus(Branch branch, Pageable pageable,
        String accountStatus);

    Map<Object, Object> getStatus(Long branchId);

    OpeningForm updateOpeningCustomer(Long id, String status);
}

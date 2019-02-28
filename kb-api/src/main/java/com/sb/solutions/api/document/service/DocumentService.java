package com.sb.solutions.api.document.service;

import org.springframework.data.domain.Page;
import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.core.service.BaseService;
import org.springframework.data.domain.Pageable;


public interface DocumentService extends BaseService<Document> {
     Page<Document> getByCycleNotContaining(LoanCycle loanCycleList, Pageable pageable);
     int getCount(LoanCycle loanCycle);
}

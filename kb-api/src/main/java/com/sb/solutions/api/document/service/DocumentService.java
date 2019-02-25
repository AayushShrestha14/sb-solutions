package com.sb.solutions.api.document.service;

import org.springframework.data.domain.Page;
import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.core.service.BaseService;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface DocumentService extends BaseService<Document> {
     Page<Document> getByCycle(Collection<LoanCycle> loanCycleList, Pageable pageable);
}

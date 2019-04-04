package com.sb.solutions.api.document.service;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.core.service.BaseService;

import java.util.List;
import java.util.Map;


public interface DocumentService extends BaseService<Document> {
     List<Document> getByCycleNotContaining(LoanCycle loanCycleList);
     Map<Object,Object> documentStatusCount();
     String saveList(List<Document> documents);
}

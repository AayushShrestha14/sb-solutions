package com.sb.solutions.api.loanDocument.repository;

import com.sb.solutions.api.loanDocument.entity.LoanDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanDocumentRepository extends JpaRepository<LoanDocument, Long> {
}

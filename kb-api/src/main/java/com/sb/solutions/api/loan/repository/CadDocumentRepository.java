package com.sb.solutions.api.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.loan.entity.CadDocument;

public interface CadDocumentRepository extends JpaRepository<CadDocument, Long> {

}

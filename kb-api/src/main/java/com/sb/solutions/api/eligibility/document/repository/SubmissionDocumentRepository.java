package com.sb.solutions.api.eligibility.document.repository;

import com.sb.solutions.api.eligibility.document.entity.SubmissionDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionDocumentRepository extends JpaRepository<SubmissionDocument, Long> {

}

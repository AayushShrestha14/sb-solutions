package com.sb.solutions.api.clientDocument.repository;

import com.sb.solutions.api.clientDocument.entity.ClientDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDocumentRepository extends JpaRepository<ClientDocument, Long> {
}

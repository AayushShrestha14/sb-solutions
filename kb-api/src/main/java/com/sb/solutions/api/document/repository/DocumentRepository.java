package com.sb.solutions.api.document.repository;

import com.sb.solutions.api.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findByName(String name);
}

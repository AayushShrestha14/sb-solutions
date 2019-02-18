package com.sb.solutions.api.document.service;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.core.service.BaseService;

public interface DocumentService extends BaseService<Document> {
    Document update( Document document);
    Document changeStatus(Document document);
    Document findByName(Document document);
}

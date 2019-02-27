package com.sb.solutions.api.document.service;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.repository.DocumentRepository;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    @Override
    public Document findOne(Long id) {
        return documentRepository.getOne(id);
    }

    @Override
    public Document save(Document document) {
        document.setStatus(Status.ACTIVE);
        return documentRepository.save(document);
    }

    @Override
    public Page<Document> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

//    @Override
//    public Page<Document> findAllPageable(Document document, Pageable pageable) {
//        return documentRepository.findAll(pageable);
//    }

    @Override
    public Document update(Document document) {
        Document doc = documentRepository.findByName(document.getName());
        doc.setName(document.getName());
        doc.setUrl(document.getUrl());
        return documentRepository.save(doc);
    }

    @Override
    public Document changeStatus(Document document) {
        Document doc = documentRepository.findByName(document.getName());
        if (doc.getStatus().equals(Status.ACTIVE)) {
            doc.setStatus(Status.INACTIVE);
        } else {
            doc.setStatus(Status.ACTIVE);
        }
        return documentRepository.save(doc);

    }

    @Override
    public Document findByName(Document document) {
        return documentRepository.findByName(document.getName());
    }

}

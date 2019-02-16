package com.sb.solutions.api.document.service;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.repository.DocumentRepository;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
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
        Document doc = documentRepository.findByName(document.getName());
        /*if (null != doc) {
            doc.setStatus(document.getStatus());
            doc.setUrl(document.getUrl());
            doc.setUserType(document.getUserType());
            return documentRepository.save(doc);
        }*/
        /*
         new document save
         by default document status will be active
          */
        document.setStatus(Status.ACTIVE);
        return documentRepository.save(document);
    }

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
    public boolean exists(Document document) {
        Document doc = documentRepository.findByName(document.getName());
        if(doc==null) {
            return  false;
        }
        else{
            return true;
        }
    }
}

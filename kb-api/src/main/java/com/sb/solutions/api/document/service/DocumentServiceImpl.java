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
        try {
            return documentRepository.findById(id).get();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Document save(Document document) {
        document.setStatus(Status.ACTIVE);
        return documentRepository.save(document);
    }

    @Override
    public Page<Document> findAllPageable(Document document, Pageable pageable) {
        return documentRepository.documentFilter(document.getName()==null?"":document.getName(),pageable);
    }


    @Override
    public Document findByName(Document document) {
        return documentRepository.findByName(document.getName());
    }


}

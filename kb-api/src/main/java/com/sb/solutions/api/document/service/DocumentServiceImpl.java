package com.sb.solutions.api.document.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.api.document.repository.DocumentRepository;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;

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
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Document save(Document document) {
        document.setLastModifiedAt(new Date());
        if (document.getId() == null) {
            document.setStatus(Status.ACTIVE);
        }
        return documentRepository.save(document);
    }

    @Override
    public Page<Document> findAllPageable(Object t, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(t, SearchDto.class);
        return documentRepository.documentFilter(s.getName() == null ? "" : s.getName(), pageable);
    }


    @Override
    public List<Document> getByCycleContaining(LoanCycle loanCycleList) {
        return documentRepository.findByLoanCycleContaining(loanCycleList);
    }

    @Override
    public Map<Object, Object> documentStatusCount() {
        return documentRepository.documentStatusCount();
    }

    @Override
    public String saveList(List<Long> ids, LoanCycle loanCycle) {
        for (Document document: documentRepository.findByLoanCycleContaining(loanCycle)){
            document.getLoanCycle().remove(loanCycle);
        }
        for (Long id : ids) {
            Document doc = documentRepository.getOne(id);
            doc.getLoanCycle().add(loanCycle);
            documentRepository.save(doc);
        }
        return "Success";
    }

    @Override
    public List<Document> getByStatus(String statusName) {
        Status status = Status.valueOf(statusName);
        return documentRepository.findByStatus(status);
    }
}

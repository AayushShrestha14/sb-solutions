package com.sb.solutions.api.document.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.sb.solutions.core.dto.SearchDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.api.document.repository.DocumentRepository;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;

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
    public Page<Document> findAllPageable(Document object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object, SearchDto.class);
        return documentRepository
            .documentFilter(s.getName() == null ? "" : s.getName(), pageable);
    }

    @Override
    public Page<Document> getByCycle(Collection<LoanCycle> loanCycleList, Pageable pageable) {
        return documentRepository.findByLoanCycleIn(loanCycleList, pageable);
    }
}

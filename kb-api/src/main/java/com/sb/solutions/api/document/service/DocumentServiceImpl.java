package com.sb.solutions.api.document.service;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.api.document.repository.DocumentRepository;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
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
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Document save(Document document) {
        document.setLastModified(new Date());
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
    public Page<Document> getByCycle(Collection<LoanCycle> loanCycleList, Pageable pageable) {
        return documentRepository.findByLoanCycleIn(loanCycleList, pageable);
    }


}
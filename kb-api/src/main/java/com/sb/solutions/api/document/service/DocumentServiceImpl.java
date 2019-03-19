package com.sb.solutions.api.document.service;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.api.document.repository.DocumentRepository;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Document save(Document document) {
        document.setLastModified(new Date());
        if(document.getId()==null){
            document.setStatus(Status.ACTIVE);
        }
        return documentRepository.save(document);
    }

    @Override
    public Page<Document> findAllPageable(Object document, Pageable pageable) {
        Document documentMapping = (Document) document;
        return documentRepository.documentFilter(documentMapping.getName()==null?"":documentMapping.getName(),pageable);
    }
    @Override
    public Page<Document> getByCycleNotContaining(LoanCycle loanCycleList, Pageable pageable){
        return documentRepository.findByLoanCycleNotContaining(loanCycleList,pageable);
    }

    @Override
    public int getCount(LoanCycle loanCycle) {
        return documentRepository.countByLoanCycle(loanCycle);
    }


}

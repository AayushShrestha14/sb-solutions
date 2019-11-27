package com.sb.solutions.api.document.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sb.solutions.core.utils.file.FileUploadUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import com.sb.solutions.api.document.repository.DocumentRepository;
import com.sb.solutions.api.document.repository.LoanCycleRepository;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final LoanCycleRepository loanCycleRepository;

    public DocumentServiceImpl(@Autowired DocumentRepository documentRepository,
        @Autowired LoanCycleRepository loanCycleRepository) {
        this.documentRepository = documentRepository;
        this.loanCycleRepository = loanCycleRepository;
    }

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
        document.setName(document.getDisplayName().trim().replaceAll("\\s", "-"));
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
    public List<Document> saveAll(List<Document> list) {
        return documentRepository.saveAll(list);
    }


    @Override
    public List<Document> getByCycleContainingAndStatus(Long loanCycleId, String statusName) {
        LoanCycle loanCycle = loanCycleRepository.getOne(loanCycleId);
        Status status = Status.valueOf(statusName);
        return documentRepository.findByLoanCycleContainingAndStatus(loanCycle, status);
    }

    @Override
    public Map<Object, Object> documentStatusCount() {
        return documentRepository.documentStatusCount();
    }

    @Override
    public String saveList(List<Long> ids, LoanCycle loanCycle) {
        Status status = Status.valueOf("ACTIVE");
        for (Document document : documentRepository
            .findByLoanCycleContainingAndStatus(loanCycle, status)) {
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

    @Override
    public Boolean downloadAllDoc(String sourcepath) {
        try {
            FileUploadUtils.createZip(sourcepath, "D:\\loanDocument.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
return true;
    }
}

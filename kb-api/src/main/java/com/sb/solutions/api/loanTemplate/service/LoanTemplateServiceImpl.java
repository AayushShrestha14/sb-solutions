package com.sb.solutions.api.loanTemplate.service;

import com.sb.solutions.api.loanTemplate.entity.LoanTemplate;
import com.sb.solutions.api.loanTemplate.repository.LoanTemplateRepository;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Rujan Maharjan on 2/25/2019
 */
@Service
public class LoanTemplateServiceImpl implements LoanTemplateService {

    @Autowired
    LoanTemplateRepository loanTemplateRepository;

    @Override
    public List<LoanTemplate> findAll() {
        return loanTemplateRepository.findAll();
    }

    @Override
    public LoanTemplate findOne(Long id) {
        return loanTemplateRepository.findById(id).get();
    }

    @Override
    public LoanTemplate save(LoanTemplate loanTemplate) {
        loanTemplate.setLastModified(new Date());
        if (loanTemplate.getId() == null) {
            loanTemplate.setStatus(Status.ACTIVE);
        }
        return loanTemplateRepository.save(loanTemplate);
    }


    @Override
    public Page<LoanTemplate> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object, SearchDto.class);
        return loanTemplateRepository.LoanTemplateFilter(s.getName()==null?"":s.getName(), pageable);
    }
}

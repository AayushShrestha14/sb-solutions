package com.sb.solutions.api.loanConfig.service;

import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.loanConfig.repository.LoanConfigRepository;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 2/26/2019
 */

@Service
public class LoanConfigServiceImpl implements LoanConfigService {

    @Autowired
    LoanConfigRepository loanConfigRepository;

    @Override
    public List<LoanConfig> findAll() {
        return loanConfigRepository.findAll();
    }

    @Override
    public LoanConfig findOne(Long id) {
        return loanConfigRepository.findById(id).get();
    }

    @Override
    public LoanConfig save(LoanConfig loanConfig) {
        loanConfig.setLastModifiedAt(new Date());
        if(loanConfig.getId()==null){
            loanConfig.setStatus(Status.ACTIVE);
        }
        loanConfig.setName(loanConfig.getName().toUpperCase());
        return loanConfigRepository.save(loanConfig);
    }

    @Override
    public Page<LoanConfig> findAllPageable(Object t, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(t, SearchDto.class);
        return loanConfigRepository.LoanConfigFilter(s.getName()==null?"":s.getName(), pageable);
    }

    @Override
    public Map<Object, Object> loanStatusCount() {
        return loanConfigRepository.loanStatusCount();
    }
}

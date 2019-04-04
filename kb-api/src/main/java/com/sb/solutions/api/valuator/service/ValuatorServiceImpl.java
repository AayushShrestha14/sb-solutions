package com.sb.solutions.api.valuator.service;

import com.sb.solutions.api.valuator.entity.Valuator;
import com.sb.solutions.api.valuator.repository.ValuatorRepository;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ValuatorServiceImpl implements ValuatorService {
    private final ValuatorRepository valuatorRepository;

    @Override
    public List<Valuator> findAll() {
        return valuatorRepository.findAll();
    }

    @Override
    public Valuator findOne(Long id) {
        return valuatorRepository.getOne(id);
    }

    @Override
    public Valuator save(Valuator valuator) {
        valuator.setLastModified(new Date());
        if(valuator.getId()==null){
            valuator.setStatus(Status.ACTIVE);
        }
        return valuatorRepository.save(valuator);
    }

    @Override
    public Page<Valuator> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object,SearchDto.class);
        return valuatorRepository.valuatorFilter(s.getName()==null?"":s.getName(),pageable);
    }

    @Override
    public Map<Object, Object> valuatorStatusCount() {
        return valuatorRepository.valuatorStatusCount();
    }
}

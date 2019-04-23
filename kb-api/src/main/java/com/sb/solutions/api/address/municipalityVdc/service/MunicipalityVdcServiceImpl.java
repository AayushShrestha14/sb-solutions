package com.sb.solutions.api.address.municipalityVdc.service;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.municipalityVdc.repository.MunicipalityVdcRepository;
import com.sb.solutions.core.dto.SearchDto;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MunicipalityVdcServiceImpl implements MunicipalityVdcService {
    private final MunicipalityVdcRepository municipality_vdcRepository;
    @Override
    public List<MunicipalityVdc> findAll() {
        return municipality_vdcRepository.findAll();
    }

    @Override
    public MunicipalityVdc findOne(Long id) {
        return municipality_vdcRepository.getOne(id);
    }

    @Override
    public MunicipalityVdc save(MunicipalityVdc municipality_vdc) {
        return municipality_vdcRepository.save(municipality_vdc);
    }

    @Override
    public Page<MunicipalityVdc> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object,SearchDto.class);
        return municipality_vdcRepository.municipalityVdcFilter(s.getName()==null?"":s.getName(),pageable);
    }

    @Override
    public List<MunicipalityVdc> findAllByDistrict(District district) {
        return municipality_vdcRepository.findAllByDistrict(district);
    }
}

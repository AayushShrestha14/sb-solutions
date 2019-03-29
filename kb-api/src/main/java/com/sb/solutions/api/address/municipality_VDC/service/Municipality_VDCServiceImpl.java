package com.sb.solutions.api.address.municipality_VDC.service;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipality_VDC.entity.Municipality_VDC;
import com.sb.solutions.api.address.municipality_VDC.repository.Municipality_VDCRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class Municipality_VDCServiceImpl implements Municipality_VDCService {
    private final Municipality_VDCRepository municipality_vdcRepository;
    @Override
    public List<Municipality_VDC> findAll() {
        return municipality_vdcRepository.findAll();
    }

    @Override
    public Municipality_VDC findOne(Long id) {
        return municipality_vdcRepository.getOne(id);
    }

    @Override
    public Municipality_VDC save(Municipality_VDC municipality_vdc) {
        return municipality_vdcRepository.save(municipality_vdc);
    }

    @Override
    public Page<Municipality_VDC> findAllPageable(Object municipality_vdc, Pageable pageable) {
        Municipality_VDC municipality_vdcMapped = (Municipality_VDC) municipality_vdc;
        return municipality_vdcRepository.municipalityVdcFilter(municipality_vdcMapped.getName()==null?"":municipality_vdcMapped.getName(),pageable);
    }

    @Override
    public List<Municipality_VDC> findAllByDistrict(District district) {
        return municipality_vdcRepository.findAllByDistrict(district);
    }
}

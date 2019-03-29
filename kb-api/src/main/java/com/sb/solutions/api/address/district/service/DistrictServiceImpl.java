package com.sb.solutions.api.address.district.service;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.district.repository.DistrictRepository;
import com.sb.solutions.api.address.province.entity.Province;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;
    @Override
    public List<District> findAll() {
        return districtRepository.findAll();
    }

    @Override
    public District findOne(Long id) {
        return districtRepository.getOne(id);
    }

    @Override
    public District save(District district) {
        return districtRepository.save(district);
    }

    @Override
    public Page<District> findAllPageable(Object district, Pageable pageable) {
        District districtMapped = (District) district;
        return districtRepository.districtFilter(districtMapped.getName()==null?"":districtMapped.getName(),pageable);
    }

    @Override
    public List<District> findAllByProvince(Province province) {
        return districtRepository.findAllByProvince(province);
    }
}

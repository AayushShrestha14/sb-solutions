package com.sb.solutions.api.address.municipality_VDC.repository;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipality_VDC.entity.Municipality_VDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Municipality_VDCRepository extends JpaRepository<Municipality_VDC,Long> {
    @Query(value = "select b from Municipality_VDC b where b.name like  concat(:name,'%')")
    Page<Municipality_VDC> municipalityVdcFilter(@Param("name")String name, Pageable pageable);

    List<Municipality_VDC> findAllByDistrict(District district);
}

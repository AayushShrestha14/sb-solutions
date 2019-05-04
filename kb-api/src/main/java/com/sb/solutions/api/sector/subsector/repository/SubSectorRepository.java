package com.sb.solutions.api.sector.subsector.repository;


import com.sb.solutions.api.sector.subsector.entity.SubSector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface SubSectorRepository extends JpaRepository<SubSector, Long> {
    @Query(value = "select\n" +
            "  (select  count(id) from sub_sector where status=1) active,\n" +
            "(select  count(id) from sub_sector where status=0) inactive,\n" +
            "(select  count(id) from sub_sector) subSectors\n", nativeQuery = true)
    Map<Object, Object> subSectorStatusCount();

    @Query(value = "select s from SubSector s where s.subSectorName like concat(:subSectorName,'%')")
    Page<SubSector> subSectorFilter(@Param("subSectorName") String subSectorName, Pageable pageable);
}

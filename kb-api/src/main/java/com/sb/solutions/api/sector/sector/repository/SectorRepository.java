package com.sb.solutions.api.sector.sector.repository;

import com.sb.solutions.api.sector.sector.entity.Sector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    @Query(value = "select\n" +
            "  (select  count(id) from Sector where status=1) active,\n" +
            "(select  count(id) from Sector where status=0) inactive,\n" +
            "(select  count(id) from Sector) sectors\n",nativeQuery = true)
    Map<Object,Object> sectorStatusCount();

    @Query(value = "select s from Sector s where s.sectorName like concat(:sectorName,'%')")
    Page<Sector> sectorFilter(@Param("sectorName")String sectorName, Pageable pageable);
}

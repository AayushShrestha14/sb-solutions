package com.sb.solutions.api.valuator.repository;

import com.sb.solutions.api.valuator.entity.Valuator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface ValuatorRepository extends JpaRepository<Valuator, Long> {

    @Query(value = "select\n" +
            "  (select  count(id) from valuator where status=1) active,\n" +
            "(select  count(id) from valuator where status=0) inactive,\n" +
            "(select  count(id) from valuator) valuators\n", nativeQuery = true)
    Map<Object, Object> valuatorStatusCount();

    @Query(value = "select v from Valuator v where v.name like concat(:name,'%')")
    Page<Valuator> valuatorFilter(@Param("name") String name, Pageable pageable);
}

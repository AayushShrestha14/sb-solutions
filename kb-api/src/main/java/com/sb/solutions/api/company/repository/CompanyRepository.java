package com.sb.solutions.api.company.repository;

import com.sb.solutions.api.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Query(value = "select\n" +
            "  (select  count(id) from Company where status=1) active,\n" +
            "(select  count(id) from Company where status=0) inactive,\n" +
            "(select  count(id) from Company) companys\n",nativeQuery = true)
    Map<Object,Object> companyStatusCount();

    @Query(value = "select c from Company c where c.name like concat(:companyName,'%')")
    Page<Company> companyFilter(@Param("companyName")String companyName, Pageable pageable);
}

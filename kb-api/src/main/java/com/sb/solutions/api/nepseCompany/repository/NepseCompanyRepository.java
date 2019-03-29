package com.sb.solutions.api.nepseCompany.repository;

import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface NepseCompanyRepository extends JpaRepository<NepseCompany,Long> {
    @Query(value = "select n from NepseCompany n where n.companyName like  concat(:companyName,'%')")
    Page<NepseCompany> nepseCompanyFilter(@Param("companyName")String companyName, Pageable pageable);
    @Query(value = "select\n" +
            "  (select  count(id) from Nepse_Company where status=1) active,\n" +
            "(select  count(id) from Nepse_Company where status=0) inactive,\n" +
            "(select  count(id) from Nepse_Company) nepses\n",nativeQuery = true)
    Map<Object,Object> nepseCompanyStatusCount();
}

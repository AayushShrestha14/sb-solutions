package com.sb.solutions.api.dms.dmsloanfile.repository;

import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.core.enums.DocStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface DmsLoanFileRepository extends JpaRepository<DmsLoanFile, Long> {

    List<DmsLoanFile> findFirst5ByDocumentStatusOrderByIdDesc(DocStatus status);


    @Query(value = "SELECT * FROM dms_loan_file d JOIN loan_config l ON d.loan_config_id=l.id " +
            "WHERE d.customer_name LIKE CONCAT(:name,'%') OR d.created_at=:date OR l.name=:loanConfig", nativeQuery = true)
    Page<DmsLoanFile> findBySearch(@Param("name") String name, @Param("date") Date date, @Param("loanConfig") String loanConfig, Pageable pageable);

    @Query(value = "select\n" +
            "(select  count(id) from dms_loan_file) pendings", nativeQuery = true)
    Map<Object, Object> pendingStatusCount();
}

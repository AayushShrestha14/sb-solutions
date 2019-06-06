package com.sb.solutions.api.dms.dmsloanfile.repository;
import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.core.enums.DocStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DmsLoanFileRepository extends JpaRepository<DmsLoanFile, Long>, JpaSpecificationExecutor<DmsLoanFile> {

    List<DmsLoanFile> findFirst5ByDocumentStatusOrderByIdDesc(DocStatus status);

    @Query(value = "select\n" +
            "(select  count(id) from dms_loan_file) pendings", nativeQuery = true)
    Map<Object, Object> pendingStatusCount();
}

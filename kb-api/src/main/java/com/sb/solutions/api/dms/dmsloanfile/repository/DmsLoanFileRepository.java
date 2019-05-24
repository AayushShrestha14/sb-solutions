package com.sb.solutions.api.dms.dmsloanfile.repository;

import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.api.memo.enums.Stage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DmsLoanFileRepository extends JpaRepository<DmsLoanFile, Long> {

    List<DmsLoanFile> findAllByStage(Stage stage);

    @Query(value = "SELECT * FROM dms_loan_file d JOIN loan_config l ON d.loan_type_id=l.id " +
            "WHERE d.customer_name LIKE CONCAT(:name,'%') OR d.created_at=:date OR l.name=:loanType", nativeQuery = true)
    Page<DmsLoanFile> findBySearch(@Param("name") String name, @Param("date") Date date, @Param("loanType") String loanType, Pageable pageable);

}

package com.sb.solutions.api.dms.dmsloanfile.repository;

import com.sb.solutions.api.memo.enums.Stage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;

import java.util.List;

@Repository
public interface DmsLoanFileRepository extends JpaRepository<DmsLoanFile, Long> {

    @Query(value = "select l from DmsLoanFile l where l.customerName like concat(:customerName,'%')")
    Page<DmsLoanFile> DmsLoanFileFilter(@Param("customerName") String name, Pageable pageable);
    List<DmsLoanFile> findAllByStage(Stage stage);
}

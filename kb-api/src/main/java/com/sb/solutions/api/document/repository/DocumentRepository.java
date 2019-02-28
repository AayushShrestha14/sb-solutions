package com.sb.solutions.api.document.repository;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query(value = "select b from Document b where b.name like concat(:name,'%')")
    Page<Document> documentFilter(@Param("name")String name, Pageable pageable);

    Page<Document> findByLoanCycleNotContaining(LoanCycle loanCycleList, Pageable pageable);
    int countByLoanCycle(LoanCycle loanCycle);

}

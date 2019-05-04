package com.sb.solutions.api.document.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.entity.LoanCycle;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query(value = "select b from Document b where b.name like concat(:name,'%')")
    Page<Document> documentFilter(@Param("name") String name, Pageable pageable);

    List<Document> findByLoanCycleNotContaining(LoanCycle loanCycleList);

    int countByLoanCycle(LoanCycle loanCycle);

    @Query(value = "select\n" +
        "  (select  count(id) from document where status=1) active,\n" +
        "(select  count(id) from document where status=0) inactive,\n" +
        "(select  count(id) from document) documents\n", nativeQuery = true)
    Map<Object, Object> documentStatusCount();

}

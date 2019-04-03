package com.sb.solutions.api.branch.repository;

import com.sb.solutions.api.branch.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 2/13/2019
 */
public interface BranchRepository extends JpaRepository<Branch,Long> {


    @Query(value = "select\n" +
        "  (select  count(id) from branch where status=1) active,\n" +
        "(select  count(id) from branch where status=0) inactive,\n" +
        "(select  count(id) from branch) branches\n",nativeQuery = true)
    Map<Object,Object> branchStatusCount();


    @Query(value = "select b from Branch b where b.name like  concat(:name,'%') or b.address like  concat(:name,'%')")
    Page<Branch> branchFilter(@Param("name")String name, Pageable pageable);

    @Query(value = "select b from Branch b where b.name like  concat(:name,'%') or b.address like  concat(:name,'%')")
    List<Branch> branchCsvFilter(@Param("name")String name);

}

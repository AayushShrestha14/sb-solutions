package com.sb.solutions.api.branch.repository;

import com.sb.solutions.api.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

/**
 * @author Rujan Maharjan on 2/13/2019
 */
public interface BranchRepository extends JpaRepository<Branch,Long> {


    @Query(value = "select\n" +
        "  (select  count(id) from branch where status=1) active,\n" +
        "(select  count(id) from branch where status=0) inactive\n" +
        "from branch limit 1",nativeQuery = true)
    Map<Object,Object> branchStatusCount();

}

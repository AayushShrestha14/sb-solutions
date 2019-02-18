package com.sb.solutions.api.branch.repository;

import com.sb.solutions.api.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rujan Maharjan on 2/13/2019
 */
public interface BranchRepository extends JpaRepository<Branch,Long> {
}

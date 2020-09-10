package com.sb.solutions.api.crg.repository;

import com.sb.solutions.api.crg.entity.CrgGroup;
import com.sb.solutions.core.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sunil Babu Shrestha on 9/10/2020
 */
public interface CrgGroupRepository extends JpaRepository<CrgGroup, Long> {

    CrgGroup findByStatus(Status status);
}

package com.sb.solutions.api.rolePermissionRight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.rolePermissionRight.entity.Rights;

/**
 * @author Rujan Maharjan on 3/31/2019
 */
public interface RightRepository extends JpaRepository<Rights, Long> {

}

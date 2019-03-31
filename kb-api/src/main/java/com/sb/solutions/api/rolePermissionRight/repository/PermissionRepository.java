package com.sb.solutions.api.rolePermissionRight.repository;


import com.sb.solutions.api.rolePermissionRight.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Rujan Maharjan on 3/25/2019.
 */

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
}

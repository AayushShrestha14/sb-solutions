package com.sb.solutions.api.rolePermissionRight.repository;


import com.sb.solutions.api.rolePermissionRight.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Rujan Maharjan on 3/25/2019.
 */

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

    @Query(value = "select p from Permission p where p.id not in (2,3,6) ")
    List<Permission>  getAllForRoleAndPermission();
}

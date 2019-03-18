package com.sb.solutions.api.roleAndPermission.repository;

import com.sb.solutions.api.roleAndPermission.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Rujan Maharjan on 3/18/2019.
 */

public interface RoleAndPermissionRepository extends JpaRepository<Role, Long> {

}
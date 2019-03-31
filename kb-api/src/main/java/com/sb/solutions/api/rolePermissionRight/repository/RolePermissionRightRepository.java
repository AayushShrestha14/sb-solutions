package com.sb.solutions.api.rolePermissionRight.repository;


import com.sb.solutions.api.rolePermissionRight.entity.RolePermissionRights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Rujan Maharjan on 3/25/2019.
 */

@Repository
public interface RolePermissionRightRepository extends JpaRepository<RolePermissionRights, Long> {

    @Query("Select p from RolePermissionRights p where p.role.id=:id and p.role.status=1 AND p.permission.status=1 order by p.permission.orders ASC ")
    List<RolePermissionRights> findByRole(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "delete from role_permission_rights where role_id=:id",nativeQuery = true)
    void deleteRolePermissionRightsByRole(@Param("id") Long id);
}

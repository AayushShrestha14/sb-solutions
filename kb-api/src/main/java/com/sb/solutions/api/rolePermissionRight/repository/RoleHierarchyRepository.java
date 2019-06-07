package com.sb.solutions.api.rolePermissionRight.repository;

import com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rujan Maharjan on 5/13/2019
 */

@Repository
public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy,Long> {

    @Query("select new com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy(r.roleOrder,r.role.roleName,r.role.id) from RoleHierarchy r  where r.roleOrder >= :id  order by r.roleOrder")
    List<RoleHierarchy>  roleHierarchyByCurrentRoleForward(@Param("id")Long id);

    @Query("select new com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy(r.roleOrder,r.role.roleName,r.role.id) from RoleHierarchy r  where r.roleOrder < :id  order by r.roleOrder")
    List<RoleHierarchy>  roleHierarchyByCurrentRoleBackward(@Param("id")Long id);

//    @Query("select new com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy(r.roleOrder,r.role.roleName,r.role.id,u.id) from RoleHierarchy r join  User u on u.role.id = r.role.id  where r.roleOrder < :id  order by r.roleOrder")


    @Query("select r from RoleHierarchy r where r.role.id=:id ")
    RoleHierarchy findByRole(@Param("id")Long id);

    @Query("select r from RoleHierarchy r where r.role.id <> 1 order by r.roleOrder ASC ")
    List<RoleHierarchy> findAll();

}

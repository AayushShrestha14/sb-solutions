package com.sb.solutions.api.rolePermissionRight.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.solutions.api.rolePermissionRight.entity.UrlApi;

/**
 * @author Rujan Maharjan on 4/20/2019
 */
public interface UrlApiRepository extends JpaRepository<UrlApi, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM role_permission_rights_api_rights where role_permission_rights_id=:id and api_rights_id=:apiId", nativeQuery = true)
    void deleteRelationRolePermissionApiRights(@Param("id") Long id, @Param("apiId") Long apiId);
}

package com.sb.solutions.api.rolePermissionRight.repository;


import com.sb.solutions.api.rolePermissionRight.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Rujan Maharjan on 3/25/2019.
 */

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "select p from Permission p where p.id not in (2,3,6) ")
    List<Permission> getAllForRoleAndPermission();

    @Query(value = "select ua.type,p.permission_name from url_api ua\n" +
            " left join role_permission_rights_api_rights apirights\n" +
            " on apirights.api_rights_id = ua.id\n" +
            "left join role_permission_rights rpr on rpr.id= apirights.role_permission_rights_id\n" +
            "left join role r on rpr.role_id = r.id\n" +
            " left join permission p on p.id = rpr.permission_id\n" +
            "where r.role_name=:role and trim(permission_name) = trim(:permName)", nativeQuery = true)
    List<Map<String, Object>> permsRight(@Param("permName") String permName, @Param("role") String role);
}

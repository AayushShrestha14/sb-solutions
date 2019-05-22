package com.sb.solutions.api.rolePermissionRight.repository;


import com.sb.solutions.api.rolePermissionRight.entity.Role;
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
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select\n" +
            "  (select  count(id) from role where status=1) active,\n" +
            "(select  count(id) from role where status=0) inactive,\n" +
            "(select  count(id) from role) roles\n", nativeQuery = true)
    Map<Object, Object> roleStatusCount();

    @Query(value = "Select distinct p.id as id,p.role_name as roleName from role p\n" +
            "left join user u on p.id = u.role_id\n" +
            "where p.status=1\n" +
            "and p.id <>:id group by p.id", nativeQuery = true)
    List<Map<Object, Object>> activeRole(@Param("id") Long id);

    @Query("select new com.sb.solutions.api.rolePermissionRight.entity.Role(r.id,r.roleName,r.status,(SELECT u.userName from User u where r.createdBy=u.id),(SELECT u.userName from User u where r.modifiedBy=u.id)) from Role r")
    List<Role>  findAll();

}

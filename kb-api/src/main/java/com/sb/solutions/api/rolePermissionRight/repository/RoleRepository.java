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
            "(select  count(id) from role) branches\n", nativeQuery = true)
    Map<Object, Object> roleStatusCount();

    @Query(value = "Select p.* from role p\n" +
            "left join user u on p.id = u.role_id\n" +
            "where p.status=1\n" +
            "and p.id <>:id", nativeQuery = true)
    List<Role> activeRole(@Param("id") Long id);

}

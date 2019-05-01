package com.sb.solutions.api.user.repository;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 12/31/2018
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select b from User b where b.name like concat(:name,'%')")
    Page<User> userFilter(@Param("name") String name, Pageable pageable);

    User getUsersByUserName(String username);

    Page<User> findByRoleIn(Collection<Role> roles, Pageable pageable);

    @Query(value = "select\n" +
            "  (select  count(id) from user where status=1) active,\n" +
            "(select  count(id) from user where status=0) inactive,\n" +
            "(select  count(id) from user) users\n", nativeQuery = true)
    Map<Object, Object> userStatusCount();

    @Query(value = "select b from User b where b.name like concat(:name,'%')")
    List<User> userCsvFilter(@Param("name") String name);
}


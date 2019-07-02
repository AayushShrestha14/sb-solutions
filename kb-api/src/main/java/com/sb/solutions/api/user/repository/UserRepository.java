package com.sb.solutions.api.user.repository;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enums.RoleAccess;
import com.sb.solutions.core.enums.Status;
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

    User getUsersByUsername(String username);

    User getUsersByUsernameAndStatus(String username, Status status);

    @Query(value = "SELECT * FROM user u JOIN user_branch ub ON ub.user_id=u.id" +
            " WHERE u.role_id=:role AND ub.branch_id IN (:branch)", nativeQuery = true)
    List<User> findByRoleIdAndBranch(@Param("role") Long role, @Param("branch") List<Long> branch);

    List<User> findByRoleRoleAccess(RoleAccess roleAccess);

    List<User> findByRoleId(Long roleId);


    Page<User> findByRoleIn(Collection<Role> roles, Pageable pageable);

    @Query(value = "select\n" +
            "  (select  count(id) from user where status=1) active,\n" +
            "(select  count(id) from user where status=0) inactive,\n" +
            "(select  count(id) from user) users\n", nativeQuery = true)
    Map<Object, Object> userStatusCount();

    @Query(value = "select b from User b where b.name like concat(:name,'%')")
    List<User> userCsvFilter(@Param("name") String name);

    @Query(value = "select ifNull(a.type,'a') from user u join role r left join role_permission_rights rpr on rpr.role_id = r.id left join role_permission_rights_api_rights rprar on rprar.role_permission_rights_id=rpr.id left join url_api a on rprar.api_rights_id = a.id where r.id = :id\n" +
            "and u.user_name=:username and a.type is not null;", nativeQuery = true)
    List<Object> userApiAuthorities(@Param("id") Long id, @Param("username") String username);

}


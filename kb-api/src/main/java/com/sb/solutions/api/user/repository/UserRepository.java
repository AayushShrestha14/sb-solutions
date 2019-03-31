package com.sb.solutions.api.user.repository;

import com.sb.solutions.api.user.entity.Role;
import com.sb.solutions.api.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * @author Rujan Maharjan on 12/31/2018
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select b from User b where b.name like concat(:name,'%')")
    Page<User> userFilter(@Param("name")String name, Pageable pageable);
    User getUsersByUserName(String username);
    Page<User> findByRoleIn(Collection<Role> roles, Pageable pageable);
}


package com.sb.solutions.api.user.repository;

import com.sb.solutions.api.user.entity.Role;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    @Override
    <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable);;
}

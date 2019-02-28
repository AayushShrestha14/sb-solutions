package com.sb.solutions.api.user.repository;

import com.sb.solutions.api.user.entity.UserType;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType,Long> {
    @Override
    <S extends UserType> Page<S> findAll(Example<S> example, Pageable pageable);;
}

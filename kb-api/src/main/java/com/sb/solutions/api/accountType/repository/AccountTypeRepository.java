package com.sb.solutions.api.accountType.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.accountType.entity.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

}

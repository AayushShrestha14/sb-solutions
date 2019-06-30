package com.sb.solutions.api.accountType.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import com.sb.solutions.api.accountType.entity.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    List<AccountType> findAllByAccountPurpose(AccountPurpose accountPurpose);
}

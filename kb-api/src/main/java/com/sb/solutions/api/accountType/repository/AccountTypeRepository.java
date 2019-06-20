package com.sb.solutions.api.accountType.repository;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import com.sb.solutions.api.accountType.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    List<AccountType> findAllByAccountPurpose(AccountPurpose accountPurpose);
}

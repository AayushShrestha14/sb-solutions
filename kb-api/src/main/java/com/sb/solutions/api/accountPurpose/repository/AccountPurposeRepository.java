package com.sb.solutions.api.accountPurpose.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;

public interface AccountPurposeRepository extends JpaRepository<AccountPurpose, Long> {

}

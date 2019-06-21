package com.sb.solutions.api.accountPurpose.repository;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountPurposeRepository extends JpaRepository<AccountPurpose, Long> {
}

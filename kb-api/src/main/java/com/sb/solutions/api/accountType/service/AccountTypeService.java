package com.sb.solutions.api.accountType.service;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import com.sb.solutions.api.accountType.entity.AccountType;
import com.sb.solutions.core.service.BaseService;

import java.util.List;

public interface AccountTypeService extends BaseService<AccountType> {
    List<AccountType> findAllByAccountPurpose(AccountPurpose accountPurpose);
}

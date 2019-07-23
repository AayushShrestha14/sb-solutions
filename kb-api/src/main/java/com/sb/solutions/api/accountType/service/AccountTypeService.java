package com.sb.solutions.api.accountType.service;

import java.util.List;

import com.sb.solutions.api.accountType.entity.AccountType;
import com.sb.solutions.core.service.BaseService;

public interface AccountTypeService extends BaseService<AccountType> {

    List<AccountType> findAllByAccountPurposeId(Long accountPurposeId);
}

package com.sb.solutions.api.security.service;

import java.util.List;
import java.util.Optional;

import com.sb.solutions.api.helper.HelperDto;
import com.sb.solutions.api.helper.HelperService;
import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.core.service.BaseService;

public interface SecurityService extends BaseService<Security>,
    HelperService<Optional<HelperDto<Long>>> {

    List<Security> saveAll(List<Security> security);
}

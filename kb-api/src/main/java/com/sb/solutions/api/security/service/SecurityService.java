package com.sb.solutions.api.security.service;

import java.util.List;

import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.core.service.BaseService;

public interface SecurityService extends BaseService<Security> {

    List<Security> saveAll(List<Security> security);
}

package com.sb.solutions.api.security.service;

import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.core.service.BaseService;

import java.util.List;

public interface SecurityService extends BaseService<Security> {
    List<Security> saveAll(List<Security> security);
}

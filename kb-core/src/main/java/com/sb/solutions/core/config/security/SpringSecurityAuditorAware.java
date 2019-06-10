package com.sb.solutions.core.config.security;

import java.util.Map;
import java.util.Optional;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sb.solutions.core.config.security.roleAndPermission.RoleAndPermissionDao;

/**
 * @author Rujan Maharjan on 4/27/2019
 */
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Autowired
    RoleAndPermissionDao roleAndPermissionDao;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper
            .convertValue(authentication.getPrincipal(), Map.class);
        if (!map.isEmpty()) {
            Long id = roleAndPermissionDao.getCurrentUserId(map.get("username").toString());
            return Optional.of(id);
        }

        throw new RuntimeException("Invalid Token");
    }
}

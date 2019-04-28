package com.sb.solutions.core.config.security;

import com.sb.solutions.core.config.security.roleAndPermission.RoleAndPermissionDao;
import com.sb.solutions.core.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author Rujan Maharjan on 4/27/2019
 */
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Autowired
    RoleAndPermissionDao roleAndPermissionDao;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User userDetail = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            Long id = roleAndPermissionDao.getCurrentUserId(userDetail.getUsername());
            return Optional.of(id);
        }
        throw new ApiException("Invalid Token");
    }
}

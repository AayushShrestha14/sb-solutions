package com.sb.solutions.core.config.security.roleAndPermission;

import com.sb.solutions.core.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rujan Maharjan on 4/29/2019
 */

@Component
public class RoleAndPermisionInterceptor implements HandlerInterceptor {
    @Autowired
    RoleAndPermissionDao roleAndPermissionDao;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean chk = roleAndPermissionDao.checkApiPermission(request.getRequestURI());

        if (!chk) {
            throw new ApiException("Permission not Granted");
        }

        return true;
    }
}

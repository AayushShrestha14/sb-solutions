package com.sb.solutions.api.logineventlisteners;

import com.sb.solutions.api.logindetail.service.LoginDetailService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginDetailService loginDetailService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String userName = event.getAuthentication().getName();
        loginDetailService.resetDetailForUser(userName);
    }
}

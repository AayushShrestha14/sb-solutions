package com.sb.solutions.core.config.security;

import com.sb.solutions.core.config.security.roleAndPermission.RoleAndPermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;
import java.util.Map;


@Configuration
@EnableResourceServer

public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    RoleAndPermissionDao roleAndPermissionDao;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("resources.solution.com").stateless(true);

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        restrictUrl(http);
        http
                .authorizeRequests()
                .antMatchers("/v1/login")
                .permitAll()
                .antMatchers("/v1/users/register")
                .permitAll()
                .antMatchers("/v1/user/test")
                .permitAll()
                .antMatchers("/v1/user/resetPassword/")
                .permitAll()
                .antMatchers("/v1/user/forgetPassword").permitAll()
                .antMatchers("/v1/**").hasAuthority("admin")
                .antMatchers("/actuator/**").hasAuthority("admin")
                .antMatchers("/v1/**")
                .authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/oauth/logout")).logoutSuccessUrl("/api/language")
        ;
    }

    public void restrictUrl(HttpSecurity http) throws Exception {
        System.out.println("refreshed");
        List<Map<String, Object>> mapList = roleAndPermissionDao.getRole();
        for (Map<String, Object> map : mapList) {
            if (map.get("api_url") != null)
                http.authorizeRequests().
                        antMatchers(map.get("api_url").toString()).hasAnyAuthority(map.get("role_name").toString());
        }
    }

}

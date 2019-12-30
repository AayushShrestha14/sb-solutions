package com.sb.solutions.core.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("resources.solution.com").stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/oauth/token")
            .permitAll()
            .antMatchers("/v1/users/register")
            .permitAll()
            .antMatchers("/v1/user/test")
            .permitAll()
            .antMatchers("/v1/user/mail")
            .permitAll()
            .antMatchers("/v1/user/resetPassword")
            .permitAll()
            .antMatchers("/v1/user/forgotPassword").permitAll()
            .antMatchers("/v1/branch/limited").permitAll()
            .antMatchers(HttpMethod.POST, "/v1/customer-otp").permitAll()
            .antMatchers(HttpMethod.POST, "/v1/customer-otp/verify").permitAll()
            .antMatchers(HttpMethod.POST, "/v1/customer-otp/regenerate").permitAll()
            .antMatchers("/v1/accountType/all").permitAll()
            .antMatchers("/v1/accountPurpose/all").permitAll()
            .antMatchers(HttpMethod.GET, "/v1/accountPurpose/accountType/*").permitAll()
            .antMatchers("/v1/accountOpening").permitAll()
            .antMatchers("/v1/accountOpening/uploadFile").permitAll()
            .antMatchers(HttpMethod.GET, "/v1/loan-configs/all/eligibility").permitAll()
            .antMatchers("/v1/loan-configs/*/eligibility").permitAll()
            .antMatchers("/v1/loan-configs/*/applicants").permitAll()
            .antMatchers("/v1/loan-configs/*/questions").permitAll()
            .antMatchers("/v1/loan-configs/*/applicants/*/documents").permitAll()
            .antMatchers(HttpMethod.GET, "/v1/eligibility-criterias").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/actuator/**").authenticated()
            .antMatchers("/swagger-ui.html").authenticated()
            .antMatchers("/v1/**")
            .authenticated()
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/oauth/logout"))
            .logoutSuccessUrl("/api/language")
        ;
    }
}

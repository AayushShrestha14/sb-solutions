package com.sb.solutions.core.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableAuthorizationServer
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityServer extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private DataSource dataSource;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired

    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore() {

        return new CustomJdbcTokenStore(dataSource);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("PermitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // clients.jdbc(dataSource);
        clients.inMemory()
            .withClient("cp-solution")
            /*.accessTokenValiditySeconds(99999911)        // expire time for access token
            .refreshTokenValiditySeconds(900000000)         // expire time for refresh token*/
            .authorizedGrantTypes("authorization_code", "refresh_token", "password",
                "client_credentials")
            .secret(passwordEncoder.encode("cpsolution123*#"))
            .scopes("read", "write", "trust")
            .redirectUris("/error")
            .resourceIds("resources.solution.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
            .authenticationManager(authenticationManager)
            .tokenStore(tokenStore())

            .userDetailsService(userDetailsService)
            .tokenServices(customTokenServices())
            .exceptionTranslator(e -> {
                if (e instanceof OAuth2Exception) {
                    final OAuth2Exception oauthException = (OAuth2Exception) e;

                    return ResponseEntity
                        .status(oauthException.getHttpErrorCode())
                        .body(new CustomOauthException(oauthException.getMessage()));
                } else if (e instanceof InternalAuthenticationServiceException) {
                    final InternalAuthenticationServiceException serviceException
                        = (InternalAuthenticationServiceException) e;
                    return ResponseEntity
                        .status(401)
                        .body(new CustomOauthException(
                            serviceException.getMessage()));
                }

                throw e;
            });
    }

    public AuthorizationServerTokenServices customTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds(9999999);
        return tokenServices;
    }


    @Bean
    public FilterRegistrationBean customCorsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }
}

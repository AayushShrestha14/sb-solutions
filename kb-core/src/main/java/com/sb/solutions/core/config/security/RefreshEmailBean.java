package com.sb.solutions.core.config.security;

import com.sb.solutions.core.config.security.roleAndPermission.RoleAndPermissionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Rujan Maharjan on 7/4/2019
 */

@Configuration
public class RefreshEmailBean {

    @Autowired
    RoleAndPermissionDao roleAndPermissionDao;

    private static final Logger logger = LoggerFactory.getLogger(RefreshEmailBean.class);

    @RefreshScope
    @Bean
    public JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        List<Map<String, Object>> map = roleAndPermissionDao.getEmailConfig();
        try {
            if (!map.isEmpty()) {
                mailSender.setHost(String.valueOf(map.get(0).get("host")));
                mailSender.setPort((Integer.parseInt(map.get(0).get("port").toString())));

                mailSender.setUsername(String.valueOf(map.get(0).get("username")));
                mailSender.setPassword(String.valueOf(map.get(0).get("password")));

                Properties props = mailSender.getJavaMailProperties();
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.debug", "true");
                props.put("mail.smtp.host", String.valueOf(map.get(0).get("host")));
                props.put("mail.smtp.port", String.valueOf(map.get(0).get("port")));
                return mailSender;

            }
        } catch (Exception e) {
            logger.error("ERROR CONFIGURATION EMAIL SETUP {}", new Date(), e.getLocalizedMessage());
        }
        return null;
    }
}

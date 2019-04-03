package com.sb.solutions.web;


import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;

/**
 * @author Rujan Maharjan on 12/27/2018
 */
@SpringBootApplication(scanBasePackages = "com.sb.solutions")
@ComponentScan("com.sb.solutions")
@EnableJpaRepositories(basePackages = "com.sb.solutions")
@EntityScan(basePackages = "com.sb.solutions")
public class CpSolutionApplication extends SpringBootServletInitializer {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(CpSolutionApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CpSolutionApplication.class);
    }
    @PostConstruct
    public void initialize() {
        if (userRepository.findAll().isEmpty()) {
            User user = new User();
            user.setName("SPADMIN");
            user.setUserName("SPADMIN");
            user.setEmail("email");
            user.setPassword("password");
            user.setStatus(Status.ACTIVE);
            user.setAccountNo("123");
            /*user.setBranch(1);*/
            user.setSignatureImage("sign");
            user.setProfilePicture("profile");
            //user.setRole(Role.SUPERADMIN);
            user.setLastModified(new Date());
            user.setPassword(passwordEncoder.encode("admin1234"));
            user.toString();
            userRepository.save(user);
            ClassPathResource schemaResource = new ClassPathResource("oauth.sql");
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(schemaResource);
            /*populator.execute(dataSource);*/
        }
    }


}

package com.sb.solutions.web;


import java.util.Date;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.enums.UserType;

/**
 * @author Rujan Maharjan on 12/27/2018
 */
@SpringBootApplication(scanBasePackages = "com.sb.solutions")
@ComponentScan("com.sb.solutions")
@EnableJpaRepositories(basePackages = "com.sb.solutions")
@EntityScan(basePackages = "com.sb.solutions")
public class CpSolutionApplication {

    @Autowired
    UserRepository userRepository;


    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Autowired
    DataSource dataSource;


    public static void main(String[] args) {
        SpringApplication.run(CpSolutionApplication.class, args);
    }


    @PostConstruct
    public void initialize() {
        if (userRepository.findAll().isEmpty()) {
            User user = new User();
            user.setName("SPADMIN");
            user.setUserName("SPADMIN");
            user.setStatus(Status.ACTIVE);
            user.setLastModifiedAt(new Date());
            user.setEmail("admin@admin.com");
            user.setPassword(passwordEncoder.encode("admin1234"));
            userRepository.save(user);


        }
    }


}

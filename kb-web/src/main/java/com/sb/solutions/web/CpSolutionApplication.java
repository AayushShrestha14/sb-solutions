package com.sb.solutions.web;


import com.sb.solutions.api.basehttp.BaseHttp;
import com.sb.solutions.api.basehttp.BaseHttpRepo;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    BaseHttpRepo baseHttpRepo;


    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Value("${server.port}")
    private String port;

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
            ClassPathResource schemaResource = new ClassPathResource("patch.sql");
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(schemaResource);
            populator.execute(dataSource);
            Role role = new Role();
            role.setId(1L);
            User user = new User();
            user.setName("SPADMIN");
            user.setUserName("SPADMIN");
            user.setStatus(Status.ACTIVE);

            user.setRole(role);
            user.setLastModified(new Date());
            user.setPassword(passwordEncoder.encode("admin1234"));
            userRepository.save(user);


            schemaResource = new ClassPathResource("oauth.sql");
            populator = new ResourceDatabasePopulator(schemaResource);
            populator.execute(dataSource);
        }
        if (baseHttpRepo.findAll().isEmpty()) {
            BaseHttp baseHttp = new BaseHttp();
            baseHttp.setBaseUrl("http://localhost:" + port + "/");
            baseHttp.setFlag(1);
            baseHttpRepo.save(baseHttp);
        }
    }


}

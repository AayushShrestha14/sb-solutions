package com.sb.solutions.web;


import com.sb.solutions.api.address.district.service.DistrictService;
import com.sb.solutions.api.address.municipalityVdc.service.MunicipalityVdcService;
import com.sb.solutions.api.address.province.service.ProvinceService;
import com.sb.solutions.api.basehttp.BaseHttp;
import com.sb.solutions.api.basehttp.BaseHttpRepo;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.core.config.security.SpringSecurityAuditorAware;
import com.sb.solutions.core.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
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
@ComponentScan(basePackages = "com.sb.solutions")
@EnableJpaRepositories(basePackages = "com.sb.solutions")
@EntityScan(basePackages = "com.sb.solutions")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class CpSolutionApplication extends SpringBootServletInitializer {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BaseHttpRepo baseHttpRepo;


    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    DataSource dataSource;
    @Autowired
    ProvinceService provinceService;
    @Autowired
    DistrictService districtService;
    @Autowired
    MunicipalityVdcService municipalityVdcService;
    @Value("${server.port}")
    private String port;

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
//            Role role = new Role();
//            role.setId(1L);
//            User user = new User();
//            user.setName("SPADMIN");
//            user.setUserName("SPADMIN");
//            user.setStatus(Status.ACTIVE);
//            user.setLastModifiedAt(new Date());
//            user.setEmail("admin@admin.com");
//            user.setRole(role);
//            user.setPassword(passwordEncoder.encode("admin1234"));
//            userRepository.save(user);


            schemaResource = new ClassPathResource("oauth.sql");
            populator = new ResourceDatabasePopulator(schemaResource);
            populator.execute(dataSource);
            schemaResource = new ClassPathResource("permissionApi.sql");
            populator = new ResourceDatabasePopulator(schemaResource);
            populator.execute(dataSource);

        }

        if (provinceService.findAll().isEmpty()) {
            ClassPathResource schemaResource = new ClassPathResource("province.sql");
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(schemaResource);
            populator.execute(dataSource);
        }

        if (districtService.findAll().isEmpty()) {
            ClassPathResource schemaResource = new ClassPathResource("district.sql");
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(schemaResource);
            populator.execute(dataSource);
        }

        if (municipalityVdcService.findAll().isEmpty()) {
            ClassPathResource schemaResource = new ClassPathResource("municipalityVdc.sql");
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(schemaResource);
            populator.execute(dataSource);
        }

        if (baseHttpRepo.findAll().isEmpty()) {
            BaseHttp baseHttp = new BaseHttp();
            baseHttp.setBaseUrl("http://localhost:" + port + "/");
            baseHttp.setFlag(1);
            baseHttpRepo.save(baseHttp);
        }
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new SpringSecurityAuditorAware();
    }
}

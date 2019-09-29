package com.sb.solutions.web;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sb.solutions.api.basehttp.BaseHttp;
import com.sb.solutions.api.basehttp.BaseHttpRepo;
import com.sb.solutions.api.productMode.entity.ProductMode;
import com.sb.solutions.api.productMode.repository.ProductModeRepository;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.repository.UserRepository;
import com.sb.solutions.core.config.security.SpringSecurityAuditorAware;
import com.sb.solutions.core.config.security.property.FileStorageProperties;
import com.sb.solutions.core.config.security.property.MailProperties;
import com.sb.solutions.core.constant.CurrentDbServer;
import com.sb.solutions.core.enums.Product;
import com.sb.solutions.core.enums.Status;

/**
 * @author Rujan Maharjan on 12/27/2018
 */
@SpringBootApplication(scanBasePackages = "com.sb.solutions")
@ComponentScan(basePackages = "com.sb.solutions")
@EnableJpaRepositories(basePackages = "com.sb.solutions")
@EntityScan(basePackages = "com.sb.solutions")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableConfigurationProperties({FileStorageProperties.class, MailProperties.class})
public class CpSolutionApplication extends SpringBootServletInitializer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BaseHttpRepo baseHttpRepo;

    @Autowired
    ProductModeRepository productModeRepository;


    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    DataSource dataSource;

    @Value("${server.port}")
    private String port;

    @Value("${spring.datasource.url}")
    private String dbValue;


    public static void main(String[] args) {
        SpringApplication.run(CpSolutionApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CpSolutionApplication.class);
    }


    @PostConstruct
    public void initialize() {
        String baseServerFolder = CurrentDbServer.currentConnectedDb(dbValue);
        InitialPatch.inital(baseServerFolder, dataSource);
        if (baseHttpRepo.findAll().isEmpty()) {
            BaseHttp baseHttp = new BaseHttp();
            baseHttp.setBaseUrl("http://" + baseHttp.getHostAddress() + ":" + port + "/");
            baseHttp.setFlag(1);
            baseHttpRepo.save(baseHttp);
        }
        ClassPathResource dataResourc = new ClassPathResource(
            baseServerFolder + "/loan_sql/patch_product_mode.sql");
        ResourceDatabasePopulator populators = new ResourceDatabasePopulator(dataResourc);
        populators.execute(dataSource);

        ProductMode productModeDMS = productModeRepository
            .getByProductAndStatus(Product.DMS, Status.ACTIVE);
        ProductMode productModeLAS = productModeRepository
            .getByProductAndStatus(Product.LAS, Status.ACTIVE);

        List<ProductMode> productModes = productModeRepository.findAll();

        for (ProductMode productMode : productModes) {

            if (productMode.getStatus().equals(Status.ACTIVE)) {

                if (productMode.getProduct().equals(Product.DMS)) {
                    ClassPathResource dataResource = new ClassPathResource(
                        baseServerFolder + "/loan_sql/patch_dms_permission.sql");
                    ClassPathResource dataResourceTemplate = new ClassPathResource(
                        baseServerFolder + "/loan_sql/dms_loan_template.sql");
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                        dataResource, dataResourceTemplate);
                    populator.execute(dataSource);
                }

                if (productMode.getProduct().equals(Product.ACCOUNT)) {
                    ClassPathResource dataResource = new ClassPathResource(
                        baseServerFolder + "/loan_sql/patch_account_opening.sql");
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                        dataResource);
                    populator.execute(dataSource);
                }

                if (productMode.getProduct().equals(Product.ELIGIBILITY)) {
                    ClassPathResource dataResource = new ClassPathResource(
                        baseServerFolder + "/loan_sql/patch_eligibility_permission.sql");
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                        dataResource);
                    populator.execute(dataSource);
                }


                if (productMode.getProduct().equals(Product.MEMO)) {
                    ClassPathResource dataResource = new ClassPathResource(
                        baseServerFolder + "/loan_sql/patch_memo.sql");
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                        dataResource);
                    populator.execute(dataSource);
                }

                if (productMode.getProduct().equals(Product.OFFER_LETTER)) {
                    ClassPathResource dataResource = new ClassPathResource(
                        baseServerFolder + "/general_patch/offer_letter.sql");
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                        dataResource);
                    populator.execute(dataSource);
                }

                if (productMode.getProduct().equals(Product.LAS)) {
                    ClassPathResource dataResource = new ClassPathResource(
                        baseServerFolder + "/loan_sql/patch_las_permission.sql");
                    ClassPathResource dataResourceTemplate = new ClassPathResource(
                        baseServerFolder + "/loan_sql/las_loan_template.sql");
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                        dataResource, dataResourceTemplate);
                    populator.execute(dataSource);
                }

            } else {
                if (productMode.getProduct().equals(Product.ACCOUNT)) {
                    ClassPathResource dataResource = new ClassPathResource(
                        baseServerFolder + "/loan_sql/patch_remove_account_opening.sql");
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                        dataResource);
                    populator.execute(dataSource);
                }

                if (productMode.getProduct().equals(Product.MEMO)) {
                    ClassPathResource dataResource = new ClassPathResource(
                        baseServerFolder + "/loan_sql/patch_remove_memo.sql");
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                        dataResource);
                    populator.execute(dataSource);
                }

                if (productMode.getProduct().equals(Product.ELIGIBILITY)) {
                    ClassPathResource dataResource = new ClassPathResource(
                        baseServerFolder + "/loan_sql/patch_remove_eligibility.sql");
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                        dataResource);
                    populator.execute(dataSource);
                }

            }

            this.permissionRemoveForDMSandLAS(productModeDMS, productModeLAS, baseServerFolder);
        }

        ClassPathResource dataResourceGeneral = new ClassPathResource(
            baseServerFolder + "/loan_sql/patch_general_permission.sql");
        ResourceDatabasePopulator populatorGeneral = new ResourceDatabasePopulator(
            dataResourceGeneral);
        populatorGeneral.execute(dataSource);

    }

    private void permissionRemoveForDMSandLAS(ProductMode dms, ProductMode las,
        String baseServerFolder) {
        if (dms != null && las == null) {
            ClassPathResource dataResource = new ClassPathResource(
                baseServerFolder + "/loan_sql/patch_remove_only_las.sql");
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(dataResource);
            populator.execute(dataSource);
        }
        if (dms == null && las == null) {
            ClassPathResource dataResource = new ClassPathResource(
                baseServerFolder + "/loan_sql/patch_remove_dms_las_permission.sql");
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(dataResource);
            populator.execute(dataSource);
        }

        Role Role = new Role();
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new SpringSecurityAuditorAware();
    }
}

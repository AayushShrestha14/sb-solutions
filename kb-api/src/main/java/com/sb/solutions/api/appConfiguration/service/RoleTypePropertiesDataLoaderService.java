package com.sb.solutions.api.appConfiguration.service;

import java.util.List;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : Rujan Maharjan on  1/6/2021
 **/

@Component
@PropertySource(value = "classpath:data/customer.properties")
@EnableConfigurationProperties
public class RoleTypePropertiesDataLoaderService {

    @Value("#{${roleTypeDefault}}")
    @Getter
    private List<String> roleTypeListDefault;

    @Value("#{${roleTypeFullCad}}")
    @Getter
    private List<String> roleTypeListFullCAD;

}

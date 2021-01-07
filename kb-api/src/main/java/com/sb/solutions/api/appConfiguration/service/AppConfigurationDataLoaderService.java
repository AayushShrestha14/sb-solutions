package com.sb.solutions.api.appConfiguration.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sb.solutions.core.utils.ProductUtils;

/**
 * @author : Rujan Maharjan on  1/6/2021
 **/
@Service
public class AppConfigurationDataLoaderService {

    private final RoleTypePropertiesDataLoaderService roleTypePropertiesDataLoaderService;

    public AppConfigurationDataLoaderService(
        RoleTypePropertiesDataLoaderService roleTypePropertiesDataLoaderService) {
        this.roleTypePropertiesDataLoaderService = roleTypePropertiesDataLoaderService;
    }

    public List<String> getRoleType() {
        if (ProductUtils.FULL_CAD) {
            return roleTypePropertiesDataLoaderService.getRoleTypeListFullCAD();
        }else{
            return roleTypePropertiesDataLoaderService.getRoleTypeListDefault();
        }
    }
}

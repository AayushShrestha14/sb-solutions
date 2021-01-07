package com.sb.solutions.web.appConfiguration.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.appConfiguration.service.AppConfigurationDataLoaderService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.web.appConfiguration.v1.constant.AppConfigConstant;

/**
 * @author : Rujan Maharjan on  1/6/2021
 **/
@RestController
@RequestMapping(AppConfigConstant.URL)
public class AppConfigurationController {


    private final AppConfigurationDataLoaderService appConfigurationDataLoaderService;

    public AppConfigurationController(
        AppConfigurationDataLoaderService appConfigurationDataLoaderService) {
        this.appConfigurationDataLoaderService = appConfigurationDataLoaderService;
    }


    @GetMapping(AppConfigConstant.ROLE_TYPE)
    public ResponseEntity<?> getRoleType() {
        return new RestResponseDto()
            .successModel(appConfigurationDataLoaderService.getRoleType());
    }

}

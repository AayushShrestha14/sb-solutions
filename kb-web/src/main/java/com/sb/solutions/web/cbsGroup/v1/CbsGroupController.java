package com.sb.solutions.web.cbsGroup.v1;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.cbsGroup.service.CbsGroupService;
import com.sb.solutions.api.cbsGroup.service.GetCbsData;
import com.sb.solutions.core.dto.RestResponseDto;

/**
 * @author : Rujan Maharjan on  12/22/2020
 **/
@RestController
@RequestMapping(CbsGroupController.URL)
public class CbsGroupController {

    static final String URL = "v1/cbs";

    private final CbsGroupService cbsGroupService;

    public CbsGroupController(CbsGroupService cbsGroupService) {
        this.cbsGroupService = cbsGroupService;
    }

    @GetMapping
    public ResponseEntity<?> getCbs() {
        return new RestResponseDto().successModel(cbsGroupService.saveAll(new ArrayList<>()));
    }

}

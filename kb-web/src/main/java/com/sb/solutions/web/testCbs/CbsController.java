package com.sb.solutions.web.testCbs;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.cbsGroup.service.GetCbsData;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.date.NepaliDate;

/**
 * @author : Rujan Maharjan on  12/20/2020
 **/

@RestController
@RequestMapping(CbsController.URL)
public class CbsController {

    static final String URL = "v1/cbs";

    private final GetCbsData getCbsData;

    public CbsController(GetCbsData getCbsData) {
        this.getCbsData = getCbsData;
    }

    @GetMapping
    public ResponseEntity<?> getCbs() {

        return new RestResponseDto().successModel(getCbsData.getAllData());
    }

}

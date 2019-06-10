package com.sb.solutions.web.common.stage.controller;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.NepaliDateConvertor.DateConverter;
import com.sb.solutions.web.loan.v1.CustomerLoanController;
import lombok.AllArgsConstructor;

/**
 * @author Rujan Maharjan on 6/9/2019
 */

@RestController
@AllArgsConstructor
@RequestMapping(value = DateController.URL)
public class DateController {

    static final String URL = "/v1/date";

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoanController.class);

    private DateConverter dateConverter;

    @GetMapping(path = "/nepDate")
    public ResponseEntity<?> getCurrentNepDate() throws ParseException {
        return new RestResponseDto().successModel(dateConverter.getCurrentNepaliDate());
    }

}

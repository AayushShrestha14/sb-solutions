package com.sb.solutions.web.common.constant.controller;

import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.enums.IncomeSource;
import com.sb.solutions.core.enums.Occupation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sunil Babu Shrestha on 8/4/2019
 */
@RestController
@RequestMapping("/enum")
public class ConstantController {

    @GetMapping("/occupation")
    public ResponseEntity<?> getOccupation() {
        return new RestResponseDto().successModel(Occupation.values());
    }


    @GetMapping("/income")
    public ResponseEntity<?> getIncomeSource() {
        return new RestResponseDto().successModel(IncomeSource.values());
    }


}

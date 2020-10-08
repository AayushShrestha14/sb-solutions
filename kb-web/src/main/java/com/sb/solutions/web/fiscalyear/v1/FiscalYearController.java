package com.sb.solutions.web.fiscalyear.v1;

import javax.xml.ws.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.fiscalyear.entity.FiscalYear;
import com.sb.solutions.api.fiscalyear.service.FiscalYearService;
import com.sb.solutions.core.dto.RestResponseDto;


/**
 * @author Bibash Bogati on 8/10/2020
 */

@RestController
@RequestMapping(FiscalYearController.URL)
public class FiscalYearController {
    static final String URL = "/v1/fiscal-year";

    private static final Logger logger = LoggerFactory.getLogger(FiscalYearController.class);

    private final FiscalYearService fiscalYearService;

    public FiscalYearController(
        FiscalYearService fiscalYearService) {
        this.fiscalYearService = fiscalYearService;
    }

    @PostMapping
    public ResponseEntity<?> saveFiscalYear(@RequestBody FiscalYear fiscalYear){
        return new RestResponseDto().successModel(fiscalYearService.save(fiscalYear));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFiscalYear() {
        return new RestResponseDto().successModel(fiscalYearService.findAll());
    }

}

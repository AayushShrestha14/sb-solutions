package com.sb.solutions.web.fiscalyear.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sb.solutions.api.fiscalyear.entity.FiscalYear;
import com.sb.solutions.api.fiscalyear.service.FiscalYearService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;

import java.util.List;


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
    public ResponseEntity<?> saveFiscalYear(@RequestBody FiscalYear fiscalYear) {
        logger.info("saving fiscal year");
        return new RestResponseDto().successModel(fiscalYearService.save(fiscalYear));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFiscalYear() {
        logger.info("getting all fiscal year");
        List<FiscalYear> fiscalYearList = fiscalYearService.findAll();
        return new RestResponseDto().successModel(fiscalYearList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new RestResponseDto().successModel(fiscalYearService.findOne(id));
    }

    @PostMapping(value = "/list")
    public ResponseEntity<?> getAllByPagination(@RequestBody Object searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        logger.info("getting fiscal year in pageable form");
        return new RestResponseDto().successModel(
            fiscalYearService.findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

}

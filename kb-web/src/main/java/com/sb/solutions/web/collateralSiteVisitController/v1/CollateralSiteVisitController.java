package com.sb.solutions.web.collateralSiteVisitController.v1;

import com.sb.solutions.api.collateralSiteVisit.entity.CollateralSiteVisit;
import com.sb.solutions.api.collateralSiteVisit.service.CollateralSiteVisitService;
import com.sb.solutions.core.dto.RestResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Mohammad Hussain on May, 2021
 */
@RestController
@RequestMapping(CollateralSiteVisitController.URL)
@Slf4j
public class CollateralSiteVisitController {
    static final String URL = "/v1/collateral-site-visit";
    private final CollateralSiteVisitService service;

    public CollateralSiteVisitController(CollateralSiteVisitService service) {
        this.service = service;
    }

    @PostMapping("/{securityId}")
    public ResponseEntity<?> save(@PathVariable("securityId") Long securityId,
                                  @RequestBody CollateralSiteVisit collateralSiteVisit) {
        return new RestResponseDto().successModel(service.saveCollateralSiteVisit(securityId, collateralSiteVisit));
    }

    @GetMapping("/{securityName}/{id}")
    public ResponseEntity<?> getCollateralSiteVisitBySecurityNameAndSecurityAndId(@PathVariable("securityName") String securityName, @PathVariable("id") Long id) {
        return new RestResponseDto().successModel(service.getCollateralSiteVisitBySecurityNameAndSecurityAndId(securityName, id));
    }

    @GetMapping("/site-visit/{siteVisitDate}/{id}")
    public ResponseEntity<?> getCollateralBySiteVisitDateAndId(@PathVariable("siteVisitDate")String siteVisitDate, @PathVariable("id") Long id) {
        LocalDate localDate = LocalDate.parse(siteVisitDate);
        return new RestResponseDto().successModel(service.getCollateralBySiteVisitDateAndId(localDate, id));
    }
}

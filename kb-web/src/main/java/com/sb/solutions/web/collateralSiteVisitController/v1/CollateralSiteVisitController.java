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

    @GetMapping("/{securityName}")
    public ResponseEntity<?> getCollateralSiteVisitBySecurityName(@PathVariable("securityName") String securityName) {
        return new RestResponseDto().successModel(service.getCollateralSiteVisitBySecurityName(securityName));
    }

    @GetMapping("/site-visit/{siteVisitDate}")
    public ResponseEntity<?> getCollateralBySiteVisitDate(@PathVariable("siteVisitDate")String siteVisitDate) {
        LocalDate localDate = LocalDate.parse(siteVisitDate);
        return new RestResponseDto().successModel(service.getCollateralBySiteVisitDate(localDate));
    }
}

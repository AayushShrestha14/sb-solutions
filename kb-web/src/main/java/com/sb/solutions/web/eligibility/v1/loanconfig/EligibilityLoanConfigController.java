package com.sb.solutions.web.eligibility.v1.loanconfig;

import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.loanConfig.service.LoanConfigService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.web.eligibility.v1.loanconfig.dto.LoanConfigDto;
import com.sb.solutions.web.eligibility.v1.loanconfig.mapper.EligibilityLoanConfigMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EligibilityLoanConfigController.URL)
@AllArgsConstructor
public class EligibilityLoanConfigController {

    static final String URL = "/v1/loan-configs";

    private final Logger logger = LoggerFactory.getLogger(EligibilityLoanConfigController.class);

    private final LoanConfigService loanConfigService;

    private final EligibilityLoanConfigMapper eligibilityLoanConfigMapper;

    @GetMapping
    public final ResponseEntity<?> getLoanConfigsForEligiblity() {
        logger.debug("Request to get the loan configs activated for eligibility.");
        final List<LoanConfig> loanConfigs = loanConfigService.getLoanConfigsActivatedForEligbility();
        final List<LoanConfigDto> loanConfigDtos = eligibilityLoanConfigMapper.mapEntitiesToDtos(loanConfigs);
        return new RestResponseDto().successModel(loanConfigDtos);
    }

}

package com.sb.solutions.web.eligibility.v1.applicant;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.eligibility.applicant.entity.Applicant;
import com.sb.solutions.api.eligibility.applicant.service.ApplicantService;
import com.sb.solutions.api.eligibility.common.EligibilityStatus;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.web.eligibility.v1.applicant.dto.ApplicantDto;
import com.sb.solutions.web.eligibility.v1.applicant.mapper.ApplicantMapper;

@RestController
@RequestMapping(ApplicantController.URL)
public class ApplicantController {

    static final String URL = "/v1/loan-configs/{loanConfigId}/applicants";

    private final Logger logger = LoggerFactory.getLogger(ApplicantController.class);


    private final ApplicantService applicantService;

    private final ApplicantMapper applicantMapper;

    public ApplicantController(ApplicantService applicantService, ApplicantMapper applicantMapper) {
        this.applicantService = applicantService;
        this.applicantMapper = applicantMapper;
    }

    @PostMapping
    public final ResponseEntity<?> saveApplicant(@Valid @RequestBody Applicant applicant,
        @PathVariable long loanConfigId) {
        logger.debug("Rest request to save the applicant information.");

        applicant.setEligibilityStatus(EligibilityStatus.NEW_REQUEST);
        final Applicant savedApplicant = applicantService.save(applicant, loanConfigId);
        if (savedApplicant == null) {
            return new RestResponseDto().failureModel("Oops! Something went wrong.");
        }
        return new RestResponseDto().successModel(applicantMapper.mapEntityToDto(savedApplicant));
    }

    @PostMapping("/update")
    public final ResponseEntity<?> updateApplicant(@RequestBody ApplicantDto applicantDto) {
        Applicant applicant = applicantService.update(applicantMapper.mapDtoToEntity(applicantDto));
        return new RestResponseDto().successModel(applicantMapper.mapEntityToDto(applicant));
    }

}

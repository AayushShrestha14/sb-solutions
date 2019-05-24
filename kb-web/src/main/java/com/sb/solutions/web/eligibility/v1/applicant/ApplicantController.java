package com.sb.solutions.web.eligibility.v1.applicant;

import com.sb.solutions.api.eligibility.applicant.entity.Applicant;
import com.sb.solutions.api.eligibility.applicant.service.ApplicantService;
import com.sb.solutions.api.eligibility.common.EligibilityStatus;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(ApplicantController.URL)
@AllArgsConstructor
public class ApplicantController {

    static final String URL = "/v1/loan-configs/{loanConfigId}/applicants";

    private final Logger logger = LoggerFactory.getLogger(ApplicantController.class);

    private final GlobalExceptionHandler globalExceptionHandler;

    private final ApplicantService applicantService;

    @PostMapping
    public final ResponseEntity<?> saveApplicant(@Valid @RequestBody Applicant applicant,
                                                 @PathVariable long loanConfigId, BindingResult bindingResult) {
        logger.debug("Rest request to save the applicant information.");
        globalExceptionHandler.constraintValidation(bindingResult);
        applicant.setEligibilityStatus(EligibilityStatus.NEW_REQUEST);
        final Applicant savedApplicant = applicantService.save(applicant);
        if (savedApplicant == null) return new RestResponseDto().failureModel("Oops! Something went wrong.");
        return new RestResponseDto().successModel(savedApplicant);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @GetMapping
    public final ResponseEntity<?> getApplicants(@RequestParam("page") int page, @RequestParam("size") int size
            , @PathVariable long loanConfigId) {
        logger.debug("Request to get all the applicants.");
        return new RestResponseDto().successModel(applicantService
                .findAllPageable(null, PaginationUtils.pageable(page, size)));
    }

    @GetMapping(path = "/{id}")
    public final ResponseEntity<?> getApplicant(@PathVariable long id, @PathVariable long loanConfigId) {
        logger.debug("Request to get the applicant with id [{}].", id);
        final Applicant applicant = applicantService.findOne(id);
        if (applicant == null) return new RestResponseDto().failureModel("Not Found.");
        return new RestResponseDto().successModel(applicant);
    }
}

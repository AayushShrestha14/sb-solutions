package com.sb.solutions.web.eligibility.v1.applicant;

import com.sb.solutions.api.eligibility.applicant.entity.Applicant;
import com.sb.solutions.api.eligibility.applicant.service.ApplicantService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminApplicantController.URL)
@AllArgsConstructor
public class AdminApplicantController {

    static final String URL = "/v1/applicants";

    private final Logger logger = LoggerFactory.getLogger(AdminApplicantController.class);

    private final ApplicantService applicantService;

    @GetMapping(path = "/{id}")
    public final ResponseEntity<?> getApplicant(@PathVariable long id) {
        logger.debug("Request to get the applicant with id [{}].", id);
        final Applicant applicant = applicantService.findOne(id);
        if (applicant == null) return new RestResponseDto().failureModel("Not Found.");
        return new RestResponseDto().successModel(applicant);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @GetMapping
    public final ResponseEntity<?> getApplicants(@RequestParam("page") int page, @RequestParam("size") int size) {
        logger.debug("Request to get all the applicants.");
        return new RestResponseDto().successModel(applicantService
                .findAllPageable(null, PaginationUtils.pageable(page, size)));
    }
}

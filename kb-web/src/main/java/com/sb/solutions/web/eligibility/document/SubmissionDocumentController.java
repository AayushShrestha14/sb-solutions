package com.sb.solutions.web.eligibility.document;

import com.sb.solutions.api.eligibility.applicant.entity.Applicant;
import com.sb.solutions.api.eligibility.applicant.service.ApplicantService;
import com.sb.solutions.api.eligibility.document.dto.DocumentDTO;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/loan-configs/{loanConfigId}/applicants/{applicantId}/documents")
public class SubmissionDocumentController {

    private final Logger logger = LoggerFactory.getLogger(SubmissionDocumentController.class);

    private final ApplicantService applicantService;

    private final GlobalExceptionHandler globalExceptionHandler;

    @PostMapping
    public final ResponseEntity<?> saveDocuments(@PathVariable long loanConfigId, @PathVariable long applicantId,
                                                 @Valid @RequestBody List<DocumentDTO> documents,
                                                 BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        logger.debug("Request to submit document for the applicant with id [{}].", applicantId);
        final Applicant updatedApplicant = applicantService.saveDocuments(documents, applicantId);
        return new RestResponseDto().successModel(updatedApplicant);
    }

}

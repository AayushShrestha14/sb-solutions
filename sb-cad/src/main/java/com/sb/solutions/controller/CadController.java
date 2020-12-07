package com.sb.solutions.controller;

import com.sb.solutions.constant.ApiConstants;
import com.sb.solutions.constant.ValidateConstants;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.validation.constraint.SbValid;
import com.sb.solutions.dto.CadStageDto;
import com.sb.solutions.service.LoanHolderService;
import com.sb.solutions.validation.constraint.CadValid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author : Rujan Maharjan on  12/1/2020
 **/

@RestController
@RequestMapping(value = ApiConstants.CAD_BASE_API)
public class CadController {

    private final LoanHolderService loanHolderService;

    public CadController(LoanHolderService loanHolderService) {
        this.loanHolderService = loanHolderService;
    }

    @PostMapping(value = ApiConstants.UNASSIGNED_LOAN)
    public ResponseEntity<?> getUnassignedLoanForCadAdmin(
        @RequestBody Map<String, String> filterParams,
        @RequestParam("page") int page,
        @RequestParam("size") int size) {
        return new RestResponseDto().successModel(loanHolderService
            .getAllUnAssignLoanForCadAdmin(filterParams, PaginationUtils.pageable(page, size)));
    }

    @PostMapping(value = ApiConstants.UNASSIGNED_LEGAL_LOAN)
    public ResponseEntity<?> getUnassignedOfferLetterLegalAdmin(
        @RequestBody Map<String, String> filterParams,
        @RequestParam("page") int page,
        @RequestParam("size") int size) {
        return new RestResponseDto().successModel(loanHolderService
            .getAllUnassignedOfferLetterForLegalAdmin(filterParams,
                PaginationUtils.pageable(page, size)));
    }


    @PostMapping(value = ApiConstants.UNASSIGNED_DISBURSEMENT_LOAN)
    public ResponseEntity<?> getUnassignedOfferLetterDisbursementAdmin(
        @RequestBody Map<String, String> filterParams,
        @RequestParam("page") int page,
        @RequestParam("size") int size) {
        return new RestResponseDto().successModel(loanHolderService
            .getAllUnassignedOfferLetterForDisbursementAdmin(filterParams,
                PaginationUtils.pageable(page, size)));
    }


    @SbValid(value = ValidateConstants.ASSIGN_VALID_PARAM)
    @PostMapping(value = ApiConstants.ASSIGN_LOAN_TO_USER)
    public ResponseEntity<?> assignLoanToUser(@RequestBody CadStageDto cadStageDto) {
        return new RestResponseDto().successModel(loanHolderService.assignLoanToUser(cadStageDto));
    }

    @SbValid(value = ValidateConstants.ACTION_VALID_PARAM)
    @CadValid
    @PostMapping(value = ApiConstants.CAD_ACTION)
    public ResponseEntity<?> action(@RequestBody CadStageDto cadStageDto) {
        return new RestResponseDto().successModel(loanHolderService.cadAction(cadStageDto));
    }

}

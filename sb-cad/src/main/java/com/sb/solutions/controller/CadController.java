package com.sb.solutions.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sb.solutions.api.authorization.approval.ApprovalRoleHierarchyService;
import com.sb.solutions.constant.ApiConstants;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.ApprovalType;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.dto.CadStageDto;
import com.sb.solutions.service.LoanHolderService;
import com.sb.solutions.validation.constraint.CadValid;


/**
 * @author : Rujan Maharjan on  12/1/2020
 **/

@RestController
@RequestMapping(value = ApiConstants.CAD_BASE_API)
public class CadController {

    private final LoanHolderService loanHolderService;
    private final ApprovalRoleHierarchyService approvalRoleHierarchyService;

    public CadController(LoanHolderService loanHolderService,
        ApprovalRoleHierarchyService approvalRoleHierarchyService) {
        this.loanHolderService = loanHolderService;
        this.approvalRoleHierarchyService = approvalRoleHierarchyService;
    }

    @PostMapping(value = ApiConstants.UNASSIGNED_LOAN)
    public ResponseEntity<?> getUnassignedLoanForCadAdmin(
        @RequestBody Map<String, String> filterParams,
        @RequestParam("page") int page,
        @RequestParam("size") int size) {
        return new RestResponseDto().successModel(loanHolderService
            .getAllUnAssignLoanForCadAdmin(filterParams, PaginationUtils.pageable(page, size)));
    }

    // use this api
    @CadValid
    @PostMapping(value = ApiConstants.ASSIGN_LOAN_TO_USER)
    public ResponseEntity<?> assignLoanToUser(@RequestBody CadStageDto cadStageDto) {
        return new RestResponseDto().successModel(loanHolderService.assignLoanToUser(cadStageDto));
    }


    @PostMapping(value = ApiConstants.CAD_ACTION)
    public ResponseEntity<?> action(@RequestBody CadStageDto cadStageDto) {
        return new RestResponseDto().successModel(loanHolderService.cadAction(cadStageDto));
    }

    @GetMapping(value = "/cad-role-list")
    public ResponseEntity<?> getRoleListPresentInCAD() {
        return new RestResponseDto()
            .successModel(
                approvalRoleHierarchyService.getRoles(ApprovalType.CAD, 0L));
    }

    // todo use this api
    @PostMapping(value = ApiConstants.CAD_LIST)
    public ResponseEntity<?> getCadList(
        @RequestBody Map<String, String> filterParams,
        @RequestParam("page") int page,
        @RequestParam("size") int size) {
        return new RestResponseDto().successModel(loanHolderService
            .getAllByFilterParams(filterParams, PaginationUtils.pageable(page, size)));
    }

}

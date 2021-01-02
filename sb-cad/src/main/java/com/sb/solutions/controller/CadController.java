package com.sb.solutions.controller;

import java.util.Map;

import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;
import com.sb.solutions.service.approvedloancaddoc.CustomerCadService;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("customerCadService")
    private final CustomerCadService customerCadService;

    public CadController(LoanHolderService loanHolderService,
                         ApprovalRoleHierarchyService approvalRoleHierarchyService, CustomerCadService customerCadService) {
        this.loanHolderService = loanHolderService;
        this.approvalRoleHierarchyService = approvalRoleHierarchyService;
        this.customerCadService = customerCadService;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCad(@PathVariable Long id) {
        return new RestResponseDto().successModel(loanHolderService.getByID(id));
    }

    @PatchMapping(ApiConstants.SAVE_CAD)
    public ResponseEntity<?> saveCAD(@RequestBody CustomerApprovedLoanCadDocumentation c) {
        return new RestResponseDto().successModel(customerCadService.save(c));
    }

}

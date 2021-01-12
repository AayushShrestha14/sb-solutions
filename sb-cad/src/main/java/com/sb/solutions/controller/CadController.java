package com.sb.solutions.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.authorization.approval.ApprovalRoleHierarchyService;
import com.sb.solutions.constant.ApiConstants;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.ApprovalType;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.dto.CadStageDto;
import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;
import com.sb.solutions.service.LoanHolderService;
import com.sb.solutions.service.approvedloancaddoc.CustomerCadService;
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
        ApprovalRoleHierarchyService approvalRoleHierarchyService,
        CustomerCadService customerCadService) {
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


    @CadValid
    @PostMapping(value = ApiConstants.ASSIGN_LOAN_TO_USER)
    public ResponseEntity<?> assignLoanToUser(@RequestBody CadStageDto cadStageDto) {
        return new RestResponseDto().successModel(loanHolderService.assignLoanToUser(cadStageDto));
    }


    @PostMapping(value = ApiConstants.CAD_ACTION)
    public ResponseEntity<?> action(@RequestBody CadStageDto cadStageDto) {
        return new RestResponseDto().successModel(loanHolderService.cadAction(cadStageDto));
    }

    @GetMapping(value = ApiConstants.CAD_ROLE_LIST)
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

    @GetMapping(value = ApiConstants.GET_BY_ID)
    public ResponseEntity<?> getCad(@PathVariable Long id) {
        return new RestResponseDto().successModel(loanHolderService.getByID(id));
    }

    @PatchMapping(ApiConstants.SAVE_CAD)
    public ResponseEntity<?> saveCAD(@RequestBody CustomerApprovedLoanCadDocumentation c) {
        return new RestResponseDto().successModel(customerCadService.save(c));
    }

    @PostMapping(path = ApiConstants.UPLOAD_FILE)
    public ResponseEntity<?> uploadOfferLetter(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("customerApprovedDocId") Long customerApprovedDocId,
        @RequestParam("offerLetterId") Long offerLetterId,
        @RequestParam(name = "type", required = true, defaultValue = "DRAFT") String type) {
        return new RestResponseDto().successModel(
            customerCadService
                .saveWithMultipartFile(multipartFile, customerApprovedDocId, offerLetterId, type));
    }


    @PostMapping(path = "/cadCheckListDocUpload")
    public ResponseEntity<?> cadCheckListDoc(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("customerInfoId") Long customerInfoId, @RequestParam("loanID") Long loanId,
        @RequestParam("customerApprovedDocId") Long customerApprovedDocId,
        @RequestParam("documentName") String documentName,
        @RequestParam("documentId") Long documentId

    ) {
        return new RestResponseDto().successModel(customerCadService
            .saveCadCheckListDoc(multipartFile, customerInfoId, loanId, customerApprovedDocId,
                documentName, documentId));
    }

    @PostMapping(value = ApiConstants.UPLOAD_ADDITIONAL_FILE)
    public ResponseEntity<?> cadCheckListDoc(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("cadId") Long cadId,
        @RequestParam("documentName") String documentName,
        @RequestParam("branchId") Long branchId,
        @RequestParam("customerInfoId") Long customerInfoId

    ) {
        return new RestResponseDto().
            successModel(customerCadService
                .uploadAdditionalDocument(multipartFile, cadId, documentName, branchId,
                    customerInfoId));
    }

}

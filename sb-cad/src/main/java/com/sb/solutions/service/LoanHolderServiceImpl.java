package com.sb.solutions.service;

import java.util.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.sb.solutions.api.authorization.approval.ApprovalRoleHierarchyService;
import com.sb.solutions.core.utils.ApprovalType;
import com.sb.solutions.core.utils.ProductUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.api.branch.service.BranchService;
import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.repository.CustomerInfoRepository;
import com.sb.solutions.api.customer.repository.specification.CustomerInfoSpecBuilder;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.constant.SuccessMessage;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.dto.CadStageDto;
import com.sb.solutions.dto.CustomerLoanDto;
import com.sb.solutions.dto.LoanHolderDto;
import com.sb.solutions.dto.StageDto;
import com.sb.solutions.entity.CadStage;
import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;
import com.sb.solutions.enums.CADDocAction;
import com.sb.solutions.enums.CadDocStatus;
import com.sb.solutions.mapper.CadStageMapper;
import com.sb.solutions.mapper.CustomerLoanMapper;
import com.sb.solutions.repository.CustomerCadRepository;
import com.sb.solutions.repository.specification.CustomerCadSpecBuilder;

/**
 * @author : Rujan Maharjan on  12/1/2020
 **/

@Service
public class LoanHolderServiceImpl implements LoanHolderService {


    private final CustomerLoanRepository customerLoanRepository;

    private final CustomerLoanMapper customerLoanMapper;

    private final UserService userService;

    private final CustomerInfoRepository customerInfoRepository;

    private final CustomerCadRepository customerCadRepository;

    private final CadStageMapper cadStageMapper;

    private final BranchService branchService;

    private final ApprovalRoleHierarchyService approvalRoleHierarchyService;


    public LoanHolderServiceImpl(CustomerLoanRepository customerLoanRepository,
                                 CustomerLoanMapper customerLoanMapper, UserService userService,
                                 CustomerInfoRepository customerInfoRepository, CustomerCadRepository customerCadRepository,
                                 CadStageMapper cadStageMapper,
                                 BranchService branchService, ApprovalRoleHierarchyService approvalRoleHierarchyService) {
        this.customerLoanRepository = customerLoanRepository;
        this.customerLoanMapper = customerLoanMapper;
        this.userService = userService;
        this.customerInfoRepository = customerInfoRepository;
        this.customerCadRepository = customerCadRepository;
        this.cadStageMapper = cadStageMapper;
        this.branchService = branchService;
        this.approvalRoleHierarchyService = approvalRoleHierarchyService;
    }

    @Override
    public Page<LoanHolderDto> getAllUnAssignLoanForCadAdmin(Map<String, String> filterParams,
        Pageable pageable) {
        String assignedLoanId = "0";
        boolean isV2 = true;
        List<LoanHolderDto> finalLoanHolderDtoList = new ArrayList<>();
        List<CustomerLoan> assignedCustomerLoan = customerCadRepository.findAllAssignedLoan();
        User user = userService.getAuthenticatedUser();
        Map<String, String> s = filterParams;
        if (!user.getRole().getRoleType().equals(RoleType.CAD_SUPERVISOR)) {
            String branchAccess = userService.getRoleAccessFilterByBranch().stream()
                .map(Object::toString).collect(Collectors.joining(","));
            if (s.containsKey("branchIds")) {
                branchAccess = s.get("branchIds");
            }
            s.put("branchIds", branchAccess);
        } else {
            String provienceList = user.getProvinces().stream().map(Province::getId)
                .map(Objects::toString).collect(Collectors.joining(","));
            filterParams.put("provinceIds", provienceList);

        }

        s.put("documentStatus", DocStatus.APPROVED.name());
        if (!assignedCustomerLoan.isEmpty()) {
            assignedLoanId = assignedCustomerLoan.stream()
                .map(a -> String.valueOf(a.getId())).collect(Collectors.joining(","));
            s.put("notLoanIds", assignedLoanId);
        }

        s.values().removeIf(Objects::isNull);

        if (isV2) {
            //using getAllUnassignedLoanV2 this function retrieve data by loan approved and grouped by customer
            return getAllUnassignedLoanV2(s, pageable);
        } else {
            // function retrieve data by customer and fetch loan of customer
            final CustomerInfoSpecBuilder customerInfoSpecBuilder = new CustomerInfoSpecBuilder(s);
            final Specification<CustomerInfo> specCustomerInfo = customerInfoSpecBuilder.build();
            Page<CustomerInfo> customerInfoPage = customerInfoRepository
                .findAll(specCustomerInfo, pageable);
            customerInfoPage.getContent().forEach(customerInfo -> {
                LoanHolderDto holderDto = new LoanHolderDto();
                holderDto.setId(customerInfo.getId());
                holderDto.setAssociateId(customerInfo.getAssociateId());
                holderDto.setName(customerInfo.getName());
                holderDto.setBranch(customerInfo.getBranch());
                holderDto.setCustomerType(customerInfo.getCustomerType());
                holderDto.setIdNumber(customerInfo.getIdNumber());
                holderDto.setIdRegDate(customerInfo.getIdRegDate());
                holderDto.setIdRegPlace(customerInfo.getIdRegPlace());
                s.put("loanHolderId", String.valueOf(customerInfo.getId()));
                final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(
                    s);
                final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
                final List<CustomerLoanDto> customerLoanDtoList = customerLoanMapper
                    .mapEntitiesToDtos(customerLoanRepository.findAll(specification));
                holderDto.setCustomerLoanDtoList(customerLoanDtoList);
                holderDto.setTotalLoan((long) customerLoanDtoList.size());
                finalLoanHolderDtoList.add(holderDto);
            });
            return new PageImpl<>(finalLoanHolderDtoList, pageable,
                customerInfoPage.getTotalElements());
        }
    }

    private Page<LoanHolderDto> getAllUnassignedLoanV2(Map<String, String> filterParams,
        Pageable pageable) {
        List<LoanHolderDto> finalLoanHolderDtoList = new ArrayList<>();
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(
            filterParams);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        final List<CustomerLoan> customerLoanDtoList = customerLoanRepository
            .findAll(specification);
        final Map<CustomerInfo, Set<CustomerLoan>> tempMap = customerLoanDtoList.stream()
            .collect(groupingBy(CustomerLoan::getLoanHolder, toSet()));
        tempMap.entrySet().forEach(e -> {
            LoanHolderDto holderDto = new LoanHolderDto();
            holderDto.setId(e.getKey().getId());
            holderDto.setAssociateId(e.getKey().getAssociateId());
            holderDto.setName(e.getKey().getName());
            holderDto.setBranch(e.getKey().getBranch());
            holderDto.setCustomerType(e.getKey().getCustomerType());
            holderDto.setIdNumber(e.getKey().getIdNumber());
            holderDto.setIdRegDate(e.getKey().getIdRegDate());
            holderDto.setIdRegPlace(e.getKey().getIdRegPlace());
            holderDto.setCustomerLoanDtoList(customerLoanMapper
                .mapEntitiesToDtos(new ArrayList<>(e.getValue())));
            holderDto.setTotalLoan((long) e.getValue().size());
            finalLoanHolderDtoList.add(holderDto);
        });
        if (finalLoanHolderDtoList.isEmpty()) {
            return new PageImpl<>(finalLoanHolderDtoList, pageable,
                0);
        }
        int start = Integer.parseInt(String.valueOf(pageable.getOffset()));
        int end = (start + pageable.getPageSize()) > finalLoanHolderDtoList.size()
            ? finalLoanHolderDtoList.size() : (start + pageable.getPageSize());
        return new PageImpl<>(finalLoanHolderDtoList.subList(start, end), pageable,
            finalLoanHolderDtoList.size());
    }

    @Override
    public String assignLoanToUser(CadStageDto cadStageDto) {
        CustomerApprovedLoanCadDocumentation temp = null;
        CustomerApprovedLoanCadDocumentation cadDocumentation = new CustomerApprovedLoanCadDocumentation();
        if (cadStageDto.getCustomerLoanDtoList().isEmpty()) {
            throw new ServiceValidationException("This customer have no any Approved loan!");
        }
        if (!ObjectUtils.isEmpty(cadStageDto.getCadId())) {
            temp = customerCadRepository.findById(cadStageDto.getCadId()).get();
            cadDocumentation = temp;
            cadDocumentation.setCadStageList(cadStageMapper
                .addPresentStageToPreviousList(cadDocumentation.getPreviousList(),
                    cadDocumentation.getCadCurrentStage()));
            cadDocumentation.setAssignedLoan(
                customerLoanMapper.mapDtosToEntities(cadStageDto.getCustomerLoanDtoList()));
            cadDocumentation
                .setCadCurrentStage(assignStage(cadStageDto.getToUser().getId(),
                    cadDocumentation.getCadCurrentStage()));


        } else {
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setId(cadStageDto.getLoanHolderId());
            cadDocumentation.setLoanHolder(customerInfo);
            cadDocumentation
                .setCadCurrentStage(assignStage(cadStageDto.getToUser().getId(), null));
            cadDocumentation.setAssignedLoan(
                customerLoanMapper.mapDtosToEntities(cadStageDto.getCustomerLoanDtoList()));
            cadDocumentation.setDocStatus(CadDocStatus.OFFER_PENDING);

        }
        customerCadRepository
            .save(cadDocumentation);
        return SuccessMessage.SUCCESS_ASSIGNED;
    }


    @Override
    public String cadAction(CadStageDto cadStageDto) {
        final User currentUser = userService.getAuthenticatedUser();
        final CustomerApprovedLoanCadDocumentation documentation = customerCadRepository
            .findById(cadStageDto.getCadId()).get();
        StageDto stageDto = cadStageMapper.cadAction(cadStageDto, documentation, currentUser);

        customerCadRepository.updateAction(cadStageDto.getCadId(),
            stageDto.getCadDocStatus(), stageDto.getCadStage(), stageDto.getPreviousList());
        return SuccessMessage.SUCCESS_ASSIGNED;
    }

    @Override
    public CustomerApprovedLoanCadDocumentation getByID(Long id) {
        return customerCadRepository.findById(id).get();
    }


    @Override
    public Page<CustomerApprovedLoanCadDocumentation> getAllByFilterParams(
        Map<String, String> filterParams, Pageable pageable) {
        filterParams.values().removeIf(Objects::isNull);
        return filterCADbyParams(filterParams, pageable);
    }

    @Override
    public String assignCadToUser(CadStageDto cadStageDto) {
        CustomerApprovedLoanCadDocumentation cadDocumentation = customerCadRepository
            .findById(cadStageDto.getCadId()).get();
        cadDocumentation.setCadStageList(cadStageMapper
            .addPresentStageToPreviousList(cadDocumentation.getPreviousList(),
                cadDocumentation.getCadCurrentStage()));
        cadDocumentation
            .setCadCurrentStage(assignStage(cadStageDto.getToUser().getId(),
                cadDocumentation.getCadCurrentStage()));
        customerCadRepository
            .save(cadDocumentation);
        return SuccessMessage.SUCCESS_ASSIGNED;
    }

    @Override
    public Map<String, Object> getCadDocumentCount(Map<String, String> filterParams) {
        Map<String , Object> data = new HashMap<>();
        User u = userService.getAuthenticatedUser();
        if (ProductUtils.OFFER_LETTER) {
            boolean isPresentInCadHierarchy = approvalRoleHierarchyService
                    .checkRoleContainInHierarchies(u.getRole().getId(), ApprovalType.CAD, 0l);
            if(isPresentInCadHierarchy || u.getRole().getRoleType() == RoleType.CAD_ADMIN
             || u.getRole().getRoleType() == RoleType.CAD_SUPERVISOR){
                String branchAccess = userService.getRoleAccessFilterByBranch().stream()
                        .map(Object::toString).collect(Collectors.joining(","));
                data.put("pendingCount" , getCountBySpec(CadDocStatus.OFFER_PENDING.name() , branchAccess));
                data.put("approvedCount" , getCountBySpec(CadDocStatus.OFFER_APPROVED.name() , branchAccess));
                data.put("legalPending" , getCountBySpec(CadDocStatus.LEGAL_PENDING.name() , branchAccess));
                data.put("legalApproved" , getCountBySpec(CadDocStatus.LEGAL_APPROVED.name() , branchAccess));
                 data.put("disbursementPending" , getCountBySpec(CadDocStatus.DISBURSEMENT_PENDING.name() , branchAccess));
                data.put("disbursementApproved" , getCountBySpec(CadDocStatus.DISBURSEMENT_APPROVED.name() , branchAccess));
                data.put("allCount" , getCountBySpec("" , branchAccess));
                data.put("showCustomerApprove" , true);
            } else {
                data.put("showCustomerApprove" , false);
            }
        } else {
            data.put("showCustomerApprove" , false);
        }
        return data;
    }

    long getCountBySpec(String docStatus , String branchAccess) {
        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("branchIds" , branchAccess);
        switch (docStatus) {
            case "OFFER_PENDING":
                //todo verify this from front and replace with enum const value
                filterParams.put("docStatus" , CadDocStatus.OFFER_PENDING.name());
                break;

            case "OFFER_APPROVED":
                filterParams.put("docStatus" , CadDocStatus.OFFER_APPROVED.name());
                break;

            case "LEGAL_PENDING":
                filterParams.put("docStatus" , CadDocStatus.LEGAL_PENDING.name());
                break;

            case "LEGAL_APPROVED":
                filterParams.put("docStatus" , CadDocStatus.LEGAL_APPROVED.name());
                break;

            case "DISBURSEMENT_PENDING":
                filterParams.put("docStatus" , CadDocStatus.DISBURSEMENT_PENDING.name());
                break;

            case "DISBURSEMENT_APPROVED":
                filterParams.put("docStatus" , CadDocStatus.DISBURSEMENT_APPROVED.name());
                break;

            case "UNASSIGNED":
                filterParams.put("cadCurrentStage" , null);
                break;

            default:
                break;
        }
        final CustomerCadSpecBuilder customerCadSpecBuilder = new CustomerCadSpecBuilder(
                branchAccessAndUserAccess(filterParams));
        final Specification<CustomerApprovedLoanCadDocumentation> specification = customerCadSpecBuilder
                .build();

        return  customerCadRepository.count(specification);
    }


    private CadStage assignStage(Long userId, CadStage cadStage) {
        User user = userService.getAuthenticatedUser();
        if (ObjectUtils.isEmpty(cadStage)) {
            cadStage = new CadStage();
        }

        User toUser = userService.findOne(userId);

        cadStage.setToRole(toUser.getRole());
        cadStage.setToUser(toUser);
        cadStage.setFromUser(user);
        cadStage.setFromRole(user.getRole());

        cadStage.setComment("Assigned to " + toUser.getUsername());
        cadStage.setDocAction(CADDocAction.ASSIGNED);
        return cadStage;
    }


    private Map<String, String> branchAccessAndUserAccess(Map<String, String> filterParams) {
        final User user = userService.getAuthenticatedUser();
        if (!user.getRole().getRoleType().equals(RoleType.CAD_SUPERVISOR)) {
            String branchAccess = userService.getRoleAccessFilterByBranch().stream()
                .map(Object::toString).collect(Collectors.joining(","));
            if (filterParams.containsKey("branchIds")) {
                branchAccess = filterParams.get("branchIds");
            }
            filterParams.put("branchIds", branchAccess);
        } else {
            String provienceList = user.getProvinces().stream().map(Province::getId)
                .map(Objects::toString).collect(Collectors.joining(","));
            filterParams.put("provinceIds", provienceList);

        }
        boolean filterByToUser = false;
        if (!filterParams.containsKey("isCadFile")) {
            filterByToUser = true;
        } else if (filterParams.get("isCadFile").equalsIgnoreCase("false")) {
            filterByToUser = true;

        }

        if (filterByToUser) {
            if (!(user.getRole().getRoleType().equals(RoleType.CAD_SUPERVISOR) || user.getRole()
                .getRoleType().equals(RoleType.CAD_ADMIN))) {
                filterParams.put("toUser", user.getId().toString());
            }
        }

        return filterParams;
    }

    private Page<CustomerApprovedLoanCadDocumentation> filterCADbyParams(
        Map<String, String> filterParams, Pageable pageable) {
        final CustomerCadSpecBuilder customerCadSpecBuilder = new CustomerCadSpecBuilder(
            branchAccessAndUserAccess(filterParams));
        final Specification<CustomerApprovedLoanCadDocumentation> specification = customerCadSpecBuilder
            .build();
        return customerCadRepository.findAll(specification, pageable);
    }

}

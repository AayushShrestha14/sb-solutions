package com.sb.solutions.service;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.repository.CustomerInfoRepository;
import com.sb.solutions.api.customer.repository.specification.CustomerInfoSpecBuilder;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.constant.ErrorMessage;
import com.sb.solutions.constant.SuccessMessage;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.dto.CadStageDto;
import com.sb.solutions.dto.CustomerLoanDto;
import com.sb.solutions.dto.LoanHolderDto;
import com.sb.solutions.dto.StageDto;
import com.sb.solutions.entity.CadStage;
import com.sb.solutions.entity.CustomerApproveLoanCadDocumentation;
import com.sb.solutions.mapper.CustomerLoanMapper;
import com.sb.solutions.mapper.CadStageMapper;
import com.sb.solutions.repository.CustomerCadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public LoanHolderServiceImpl(CustomerLoanRepository customerLoanRepository, CustomerLoanMapper customerLoanMapper, UserService userService, CustomerInfoRepository customerInfoRepository, CustomerCadRepository customerCadRepository, CadStageMapper cadStageMapper) {
        this.customerLoanRepository = customerLoanRepository;
        this.customerLoanMapper = customerLoanMapper;
        this.userService = userService;
        this.customerInfoRepository = customerInfoRepository;
        this.customerCadRepository = customerCadRepository;
        this.cadStageMapper = cadStageMapper;
    }

    @Override
    public Page<LoanHolderDto> getAllUnAssignLoanForCadAdmin(Map<String, String> filterParams, Pageable pageable) {
        List<LoanHolderDto> finalLoanHolderDtoList = new ArrayList<>();
        Map<String, String> s = filterParams;
        String branchAccess = userService.getRoleAccessFilterByBranch().stream()
                .map(Object::toString).collect(Collectors.joining(","));
        if (s.containsKey("branchIds")) {
            branchAccess = s.get("branchIds");
        }
        s.put("branchIds", branchAccess);
        s.put("documentStatus", DocStatus.APPROVED.name());
        // s.put("postApprovalAssignStatus", PostApprovalAssignStatus.NOT_ASSIGNED.name());
        s.values().removeIf(Objects::isNull);
        final CustomerInfoSpecBuilder customerInfoSpecBuilder = new CustomerInfoSpecBuilder(s);
        final Specification<CustomerInfo> specCustomerInfo = customerInfoSpecBuilder.build();
        Page<CustomerInfo> customerInfoPage = customerInfoRepository.findAll(specCustomerInfo, pageable);
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
            final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
            final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
            final List<CustomerLoanDto> customerLoanDtoList = customerLoanMapper.mapEntitiesToDtos(customerLoanRepository.findAll(specification));
            holderDto.setCustomerLoanDtoList(customerLoanDtoList);
            holderDto.setTotalLoan((long) customerLoanDtoList.size());
            finalLoanHolderDtoList.add(holderDto);
        });
        return new PageImpl<>(finalLoanHolderDtoList, pageable, customerInfoPage.getTotalElements());
    }

    @Override
    public String assignLoanToUser(CadStageDto cadStageDto) {
        boolean isUniqueCADDocumentType = false;
        CustomerApproveLoanCadDocumentation temp = null;
        CustomerApproveLoanCadDocumentation cadDocumentation = new CustomerApproveLoanCadDocumentation();
        cadDocumentation.setAssignedLoan(customerLoanMapper.mapDtosToEntities(cadStageDto.getCustomerLoanDtoList()));
        cadDocumentation.setCadCurrentStage(assignStage(cadStageDto.getToUser().getId()));
        cadDocumentation.setCadDocumentType(cadStageDto.getDocumentType());
        if (!ObjectUtils.isEmpty(cadStageDto.getCadId())) {
            temp = customerCadRepository.findById(cadStageDto.getCadId()).get();
            if ((temp.getCadDocumentType().equals(cadStageDto.getDocumentType()))) {
                if ((!temp.getDocStatus().equals(DocStatus.APPROVED))) {
                    cadDocumentation = temp;
                    cadDocumentation.setCadStageList(cadStageMapper.addPresentListToPreviousList(cadDocumentation.getPreviousList(), cadDocumentation.getCadCurrentStage()));
                    cadDocumentation.setAssignedLoan(customerLoanMapper.mapDtosToEntities(cadStageDto.getCustomerLoanDtoList()));
                    cadDocumentation.setCadCurrentStage(assignStage(cadStageDto.getToUser().getId()));

                } else {
                    return ErrorMessage.ERROR_ASSIGNED_APPROVED_ALREADY;
                }

            } else {
                cadDocumentation.setParentId(cadStageDto.getCadId());
                cadDocumentation.setAssignedLoan(temp.getAssignedLoan());
                isUniqueCADDocumentType = true;
            }
        }
        final CustomerApproveLoanCadDocumentation cadDocumentation1 = customerCadRepository.save(cadDocumentation);
        if (!ObjectUtils.isEmpty(cadDocumentation1)) {
            if (isUniqueCADDocumentType && (!ObjectUtils.isEmpty(temp))) {
                temp.setChildId(cadDocumentation1.getId());
                customerCadRepository.save(temp);
            }
            return SuccessMessage.SUCCESS_ASSIGNED;

        } else {
            return ErrorMessage.ERROR_ASSIGNED;
        }
    }

    @Override
    public String cadAction(CadStageDto cadStageDto) {
        final User currentUser = userService.getAuthenticatedUser();
        final CustomerApproveLoanCadDocumentation documentation = customerCadRepository.getOne(cadStageDto.getCadId());
        StageDto stageDto = cadStageMapper.cadAction(cadStageDto, documentation, currentUser);
        return SuccessMessage.SUCCESS_ASSIGNED;
    }


    private CadStage assignStage(Long userId) {
        User user = userService.getAuthenticatedUser();
        CadStage cadStage = new CadStage();
        User toUser = userService.findOne(userId);

        cadStage.setToRole(toUser.getRole());
        cadStage.setToUser(toUser);
        cadStage.setFromUser(user);
        cadStage.setFromRole(user.getRole());

        cadStage.setComment("Assigned to " + toUser.getUsername());
        cadStage.setDocAction(DocAction.ASSIGNED);
        return cadStage;
    }


}

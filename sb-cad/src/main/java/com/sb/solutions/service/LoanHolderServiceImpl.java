package com.sb.solutions.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.api.authorization.approval.ApprovalRoleHierarchyService;
import com.sb.solutions.api.authorization.entity.Role;
import com.sb.solutions.api.authorization.service.RoleService;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.repository.CustomerInfoRepository;
import com.sb.solutions.api.customer.repository.specification.CustomerInfoSpecBuilder;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.dto.CustomerLoanFilterDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.proposal.entity.Proposal;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.constant.SuccessMessage;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.repository.customCriteria.BaseCriteriaQuery;
import com.sb.solutions.core.repository.customCriteria.dto.CriteriaDto;
import com.sb.solutions.core.utils.ApprovalType;
import com.sb.solutions.core.utils.FilterJsonUtils;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.ProductUtils;
import com.sb.solutions.dto.CadAssignedLoanDto;
import com.sb.solutions.dto.CadListDto;
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
@Slf4j
public class LoanHolderServiceImpl implements LoanHolderService {

    private static final String[] CAD_CUSTOMER_LOAN = {"id", "loan.name", "loanType",
        "documentStatus", "currentStage.toUser.name", "currentStage.lastModifiedAt",
        "priority", "previousStageList", "combinedLoan", "proposal.proposedLimit", "loan.id",
        "loanHolder.id"};

    private static final String[] CAD_FILTER_COLUMN = {"id", "cadCurrentStage", "cadStageList",
        "docStatus", "loanHolder.branch.id", "loanHolder.branch.name",
        "loanHolder.branch.province.name", "loanHolder.name",
        "loanHolder.customerType", "loanHolder.clientType", "isAdditionalDisbursement",
        "lastModifiedAt","cadCurrentStage.toUser.name"};
    private static final String[] CAD_FILTER_JOIN_COLUMN = {"assignedLoan"};

    private static final String[] CAD_CUSTOMER_LOAN_JOIN = {"combinedLoan", "proposal",
        "currentStage", "loan", "loanHolder"};

    private final CustomerLoanRepository customerLoanRepository;

    private final CustomerLoanMapper customerLoanMapper;

    private final UserService userService;

    private final CustomerInfoRepository customerInfoRepository;

    private final CustomerCadRepository customerCadRepository;

    private final CadStageMapper cadStageMapper;


    private final ApprovalRoleHierarchyService approvalRoleHierarchyService;

    private final RoleService roleService;

    private final EntityManager em;


    public LoanHolderServiceImpl(CustomerLoanRepository customerLoanRepository,
        CustomerLoanMapper customerLoanMapper, UserService userService,
        CustomerInfoRepository customerInfoRepository, CustomerCadRepository customerCadRepository,
        CadStageMapper cadStageMapper, ApprovalRoleHierarchyService approvalRoleHierarchyService,
        RoleService roleService, EntityManager em) {
        this.customerLoanRepository = customerLoanRepository;
        this.customerLoanMapper = customerLoanMapper;
        this.userService = userService;
        this.customerInfoRepository = customerInfoRepository;
        this.customerCadRepository = customerCadRepository;
        this.cadStageMapper = cadStageMapper;
        this.approvalRoleHierarchyService = approvalRoleHierarchyService;
        this.roleService = roleService;
        this.em = em;
    }

    @Override
    public Page<LoanHolderDto> getAllUnAssignLoanForCadAdmin(Map<String, String> filterParams,
        Pageable pageable) {
        String assignedLoanId = "0";
        boolean isV2 = true;
        List<LoanHolderDto> finalLoanHolderDtoList = new ArrayList<>();
        List<Long> assignedCustomerLoanIds = customerCadRepository.findAllAssignedLoanIds();
        User user = userService.getAuthenticatedUser();
        Map<String, String> s = filterParams;
        if (!user.getRole().getRoleType().equals(RoleType.CAD_SUPERVISOR)) {

            String branchAccess = userService.getRoleAccessFilterByBranch().stream()
                .map(Object::toString).collect(Collectors.joining(","));
            if (s.containsKey("branchIds") && !ObjectUtils.isEmpty(s.get("branchIds"))) {
                branchAccess = s.get("branchIds");
            }
            s.put("branchIds", branchAccess);

        } else {
            String provienceList = user.getProvinces().stream().map(Province::getId)
                .map(Objects::toString).collect(Collectors.joining(","));
            filterParams.put("provinceIds", provienceList);

        }


        s.put("documentStatus", DocStatus.APPROVED.name());
        if (!assignedCustomerLoanIds.isEmpty()) {
            assignedLoanId = assignedCustomerLoanIds.stream()
                .map(String::valueOf).collect(Collectors.joining(","));
            s.put("notLoanIds", assignedLoanId);
        }

        s.values().removeIf(Objects::isNull);

        if (isV2) {
            //using getAllUnassignedLoanV2 this function retrieve data by loan approved and grouped by customer
            // return getAllUnassignedLoanV2(s, pageable);
            return getUnassignedCustomerV3(s, pageable);
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
    public Page<?> getAllByFilterParams(
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
    public String saveAdditionalDisbursement(CustomerApprovedLoanCadDocumentation c, Long roleId) {
        log.info("saving additional Disbursement::for cadId:: {} to roleId:: {}", c.getId(),
            roleId);
        final User user = userService.getAuthenticatedUser();
        c.setCadStageList(cadStageMapper
            .addPresentStageToPreviousList(c.getPreviousList(),
                c.getCadCurrentStage()));
        CadStage cadStage = c.getCadCurrentStage();
        final Role role = roleService.findOne(roleId);
        if (role.getRoleType().equals(RoleType.CAD_LEGAL)) {
            c.setDocStatus(CadDocStatus.LEGAL_PENDING);
        } else {
            c.setDocStatus(CadDocStatus.DISBURSEMENT_PENDING);
        }
        cadStage.setToRole(role);
        cadStage.setToUser(null);
        cadStage.setDocAction(CADDocAction.FORWARD);
        cadStage.setFromUser(user);
        cadStage.setFromRole(user.getRole());
        c.setCadCurrentStage(cadStage);
        c.setIsAdditionalDisbursement(true);
        customerCadRepository
            .save(c);
        return SuccessMessage.SUCCESS_FORWARD;
    }

    @Override
    public Map<String, Object> getCadDocumentCount(Map<String, String> filterParams) {
        Map<String, Object> data = new HashMap<>();
        User u = userService.getAuthenticatedUser();
        if (ProductUtils.OFFER_LETTER) {
            boolean isPresentInCadHierarchy = approvalRoleHierarchyService
                .checkRoleContainInHierarchies(u.getRole().getId(), ApprovalType.CAD, 0l);
            if (isPresentInCadHierarchy || u.getRole().getRoleType() == RoleType.CAD_ADMIN
                || u.getRole().getRoleType() == RoleType.CAD_SUPERVISOR) {
                String branchAccess = userService.getRoleAccessFilterByBranch().stream()
                    .map(Object::toString).collect(Collectors.joining(","));
                data.put("pendingCount",
                    getCountBySpec(CadDocStatus.OFFER_PENDING.name(), branchAccess));
                data.put("approvedCount",
                    getCountBySpec(CadDocStatus.OFFER_APPROVED.name(), branchAccess));
                data.put("legalPending",
                    getCountBySpec(CadDocStatus.LEGAL_PENDING.name(), branchAccess));
                data.put("legalApproved",
                    getCountBySpec(CadDocStatus.LEGAL_APPROVED.name(), branchAccess));
                data.put("disbursementPending",
                    getCountBySpec(CadDocStatus.DISBURSEMENT_PENDING.name(), branchAccess));
                data.put("disbursementApproved",
                    getCountBySpec(CadDocStatus.DISBURSEMENT_APPROVED.name(), branchAccess));
                data.put("allCount", getCountBySpec("", branchAccess));
                data.put("showCustomerApprove", true);
            } else {
                data.put("showCustomerApprove", false);
            }
        } else {
            data.put("showCustomerApprove", false);
        }
        return data;
    }

    long getCountBySpec(String docStatus, String branchAccess) {
        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("branchIds", branchAccess);
        switch (docStatus) {
            case "OFFER_PENDING":
                //todo verify this from front and replace with enum const value
                filterParams.put("docStatus", CadDocStatus.OFFER_PENDING.name());
                break;

            case "OFFER_APPROVED":
                filterParams.put("docStatus", CadDocStatus.OFFER_APPROVED.name());
                break;

            case "LEGAL_PENDING":
                filterParams.put("docStatus", CadDocStatus.LEGAL_PENDING.name());
                break;

            case "LEGAL_APPROVED":
                filterParams.put("docStatus", CadDocStatus.LEGAL_APPROVED.name());
                break;

            case "DISBURSEMENT_PENDING":
                filterParams.put("docStatus", CadDocStatus.DISBURSEMENT_PENDING.name());
                break;

            case "DISBURSEMENT_APPROVED":
                filterParams.put("docStatus", CadDocStatus.DISBURSEMENT_APPROVED.name());
                break;

            case "UNASSIGNED":
                filterParams.put("cadCurrentStage", null);
                break;

            default:
                break;
        }
        final CustomerCadSpecBuilder customerCadSpecBuilder = new CustomerCadSpecBuilder(
            branchAccessAndUserAccess(filterParams));
        final Specification<CustomerApprovedLoanCadDocumentation> specification = customerCadSpecBuilder
            .build();

        return customerCadRepository.count(specification);
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
            if (filterParams.containsKey("branchIds") && !ObjectUtils
                .isEmpty(filterParams.get("branchIds"))) {
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
                .getRoleType().equals(RoleType.CAD_ADMIN) || user.getRole()
                .getRoleType().equals(RoleType.CAD_LEGAL))) {
                filterParams.put("toUser", user.getId().toString());
            }
        }

        if ((user.getRole().getRoleType().equals(RoleType.CAD_LEGAL)) && filterByToUser) {
            filterParams.put("toRole", user.getRole().getId().toString());
        }

        return filterParams;
    }

    private Page<?> filterCADbyParams(
        Map<String, String> filterParams, Pageable pageable) {
        String sortBy = "cadCurrentStage.toUser.name";
        String orderBy = BaseCriteriaQuery.ASC;
        final CustomerCadSpecBuilder customerCadSpecBuilder = new CustomerCadSpecBuilder(
            branchAccessAndUserAccess(filterParams));
        final Specification<CustomerApprovedLoanCadDocumentation> specification = customerCadSpecBuilder
            .build();
        CriteriaDto<CustomerApprovedLoanCadDocumentation, CadListDto> criteriaDto = new CriteriaDto<>(
            CustomerApprovedLoanCadDocumentation.class, CadListDto.class, specification,
            CAD_FILTER_COLUMN,
            CAD_FILTER_JOIN_COLUMN);
        if (filterParams.containsKey(PaginationUtils.SORT_BY)) {
            sortBy = ObjectUtils.isEmpty(filterParams.get(PaginationUtils.SORT_BY)) ? "docStatus"
                : filterParams.get(PaginationUtils.SORT_BY);
            orderBy = ObjectUtils.isEmpty(filterParams.get(PaginationUtils.SORT_ORDER))
                ? BaseCriteriaQuery.ASC
                : filterParams.get(PaginationUtils.SORT_ORDER);
        }
        BaseCriteriaQuery<CustomerApprovedLoanCadDocumentation, CadListDto> baseCriteriaQuery = new BaseCriteriaQuery<>();
        Page<CadListDto> cadListDtoPage = baseCriteriaQuery
            .getListPage(criteriaDto, em, pageable, sortBy, orderBy);
        List<Long> ids = cadListDtoPage.getContent().stream().map(CadListDto::getId)
            .collect(Collectors.toList());
        List<CadAssignedLoanDto> testData = customerCadRepository.findAssignedLoanByIdIn(ids);
        cadListDtoPage.getContent().forEach(cadListDto -> {
            cadListDto.setAssignedLoan(CadAssignedLoanDto.getAssigned(testData.stream()
                .filter(t -> cadListDto.getId().equals(t.getCadId()))
                .collect(Collectors.toList()), cadListDto.getLoanHolder()));
        });
        return cadListDtoPage;
        //return customerCadRepository.findAll(specification, pageable);
    }


    private Page<LoanHolderDto> getUnassignedCustomerV3(Map<String, String> filterParams,
        Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LoanHolderDto> q = cb.createQuery(LoanHolderDto.class);
        Root<CustomerLoan> root = q.from(CustomerLoan.class);

        q.select(
            cb.construct(
                LoanHolderDto.class,
                root.get("loanHolder").get("id"),
                root.get("loanHolder").get("customerType"),
                root.get("loanHolder").get("name"),
                root.get("loanHolder").get("associateId"),
                root.get("loanHolder").get("idNumber"),
                root.get("loanHolder").get("idRegDate"),
                root.get("loanHolder").get("idRegPlace"),
                root.join("loanHolder").join("branch").get("name"),
                root.join("loanHolder").join("branch").join("province").get("name"),
                root.join("loanHolder").join("branch").get("id")
            )).distinct(true);
        q.orderBy(cb.asc(root.get("loanHolder").get("name")));
        CustomerLoanSpecBuilder customerLoanSpecBuilderInner = new CustomerLoanSpecBuilder(
            filterParams);
        Specification<CustomerLoan> innerSpec = customerLoanSpecBuilderInner.build();
        List<LoanHolderDto> resultList = em
            .createQuery(q.where(innerSpec.toPredicate(root, q, cb)))
            .setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize())
            .getResultList();

        String loanHolderIds = resultList.stream()
            .map(a -> String.valueOf(a.getId())).collect(Collectors.joining(","));

        filterParams.put("loanHolderIdIn", loanHolderIds);
        CustomerLoanSpecBuilder customerLoanSpecBuilderEachCustomer = new CustomerLoanSpecBuilder(
            filterParams);
        Specification<CustomerLoan> innerSpecEachCustomer = customerLoanSpecBuilderEachCustomer
            .build();
        List<CustomerLoan> loanDetailList = getLoanOfEachCustomer(innerSpecEachCustomer);

        resultList.forEach(r -> {
            Province p = new Province();
            p.setName(r.getProvinceName());
            Branch b = new Branch();
            b.setName(r.getBranchName());
            b.setId(r.getBranchId());
            b.setProvince(p);
            r.setBranch(b);
            List<CustomerLoan> loanDetailListCustomerWise = loanDetailList.stream()
                .filter(customerLoan -> Objects
                    .equals(customerLoan.getLoanHolder().getId(), r.getId()))
                .collect(
                    Collectors.toList());
            r.setCustomerLoanDtoList(customerLoanMapper
                .mapEntitiesToDtos(loanDetailListCustomerWise));
            r.setTotalLoan((long) loanDetailListCustomerWise.size());
        });

        // Create Count Query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CustomerLoan> customerLoanRootCount = countQuery.from(CustomerLoan.class);
        countQuery.select(
            cb.countDistinct(customerLoanRootCount.get("loanHolder").get("id")));
        Long count = em.createQuery(
            countQuery.where(innerSpec.toPredicate(customerLoanRootCount, countQuery, cb)))
            .getSingleResult();
        return new PageImpl<>(resultList, pageable, count);
    }

    private List<CustomerLoan> getLoanOfEachCustomer(
        Specification<CustomerLoan> innerSpec) {

        CriteriaDto<CustomerLoan, CustomerLoanFilterDto> criteriaDto = new CriteriaDto<>(
            CustomerLoan.class, CustomerLoanFilterDto.class, innerSpec, CAD_CUSTOMER_LOAN,
            CAD_CUSTOMER_LOAN_JOIN);
        BaseCriteriaQuery<CustomerLoan, CustomerLoanFilterDto> baseCriteriaQuery = new BaseCriteriaQuery<>();
        List<CustomerLoanFilterDto> list = baseCriteriaQuery.getList(criteriaDto, em);
        List<CustomerLoan> customerLoanList = new ArrayList<>();
        list.forEach(l -> {
            CustomerLoan customerLoan = new CustomerLoan();

            LoanConfig loanConfig = new LoanConfig();
            loanConfig.setId(l.getLoanId());
            loanConfig.setName(l.getLoanName());
            customerLoan.setLoan(loanConfig);

            Proposal p = new Proposal();
            p.setProposedLimit(l.getProposedLimit());
            customerLoan.setProposal(p);

            LoanStage loanStage = new LoanStage();
            User user = new User();
            user.setName(l.getToUserName());
            loanStage.setToUser(user);
            loanStage.setLastModifiedAt(l.getLastModifiedAt());
            customerLoan.setCurrentStage(loanStage);

            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setId(l.getLoanHolderId());
            customerLoan.setLoanHolder(customerInfo);

            customerLoan.setId(l.getId());
            customerLoan.setDocumentStatus(l.getDocumentStatus());
            customerLoan.setPreviousList(l.getPreviousList());
            customerLoan.setLoanType(l.getLoanType());

            customerLoanList.add(customerLoan);
        });
        return customerLoanList.stream().filter(FilterJsonUtils.distinctByKey(CustomerLoan::getId))
            .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getStat() {
        System.out.println();
        return customerCadRepository.getStat();
    }

}

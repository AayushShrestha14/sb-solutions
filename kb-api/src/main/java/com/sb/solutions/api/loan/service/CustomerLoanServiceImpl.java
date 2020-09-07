package com.sb.solutions.api.loan.service;

import static com.sb.solutions.core.constant.AppConstant.SEPERATOR_BLANK;
import static com.sb.solutions.core.constant.AppConstant.SEPERATOR_FRONT_SLASH;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.sb.solutions.api.approvallimit.emuns.LoanApprovalType;
import com.sb.solutions.api.companyInfo.model.entity.CompanyInfo;
import com.sb.solutions.api.companyInfo.model.service.CompanyInfoService;
import com.sb.solutions.api.creditRiskGrading.service.CreditRiskGradingService;
import com.sb.solutions.api.creditRiskGradingAlpha.service.CreditRiskGradingAlphaService;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.api.customer.service.CustomerInfoService;
import com.sb.solutions.api.customer.service.CustomerService;
import com.sb.solutions.api.customerGroup.CustomerGroup;
import com.sb.solutions.api.customerRelative.entity.CustomerRelative;
import com.sb.solutions.api.dms.dmsloanfile.service.DmsLoanFileService;
import com.sb.solutions.api.financial.service.FinancialService;
import com.sb.solutions.api.group.service.GroupServices;
import com.sb.solutions.api.guarantor.entity.Guarantor;
import com.sb.solutions.api.insurance.entity.Insurance;
import com.sb.solutions.api.insurance.service.InsuranceService;
import com.sb.solutions.api.loan.CustomerLoanGroupDto;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.PieChartDto;
import com.sb.solutions.api.loan.StatisticDto;
import com.sb.solutions.api.loan.dto.CustomerLoanCsvDto;
import com.sb.solutions.api.loan.dto.CustomerOfferLetterDto;
import com.sb.solutions.api.loan.dto.LoanStageDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.entity.CustomerOfferLetter;
import com.sb.solutions.api.loan.mapper.NepaliTemplateMapper;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.loanflag.entity.CustomerLoanFlag;
import com.sb.solutions.api.loanflag.service.CustomerLoanFlagService;
import com.sb.solutions.api.mawCreditRiskGrading.service.MawCreditRiskGradingService;
import com.sb.solutions.api.nepalitemplate.entity.NepaliTemplate;
import com.sb.solutions.api.nepalitemplate.service.NepaliTemplateService;
import com.sb.solutions.api.preference.notificationMaster.entity.NotificationMaster;
import com.sb.solutions.api.preference.notificationMaster.repository.spec.NotificationMasterSpec;
import com.sb.solutions.api.preference.notificationMaster.service.NotificationMasterService;
import com.sb.solutions.api.proposal.service.ProposalService;
import com.sb.solutions.api.security.service.SecurityService;
import com.sb.solutions.api.sharesecurity.ShareSecurity;
import com.sb.solutions.api.sharesecurity.service.ShareSecurityService;
import com.sb.solutions.api.siteVisit.service.SiteVisitService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.api.vehiclesecurity.service.VehicleSecurityService;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanFlag;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.core.enums.NotificationMasterType;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.PathBuilder;
import com.sb.solutions.core.utils.ProductUtils;
import com.sb.solutions.core.utils.string.StringUtil;
import com.sb.solutions.report.core.bean.ReportParam;
import com.sb.solutions.report.core.enums.ExportType;
import com.sb.solutions.report.core.enums.ReportType;
import com.sb.solutions.report.core.factory.ReportFactory;
import com.sb.solutions.report.core.model.Report;


/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Service
public class CustomerLoanServiceImpl implements CustomerLoanService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoanServiceImpl.class);
    private final CustomerLoanRepository customerLoanRepository;
    private final UserService userService;
    private final CustomerService customerService;
    private final DmsLoanFileService dmsLoanFileService;
    private final CompanyInfoService companyInfoService;
    private final SiteVisitService siteVisitService;
    private final FinancialService financialService;
    private final MawCreditRiskGradingService mawCreditRiskGradingService;
    private final CreditRiskGradingAlphaService creditRiskGradingAlphaService;
    private final SecurityService securityService;
    private final ProposalService proposalService;
    private final GroupServices groupService;
    private final CustomerOfferService customerOfferService;
    private final CreditRiskGradingService creditRiskGradingService;
    private final VehicleSecurityService vehicleSecurityService;
    private final ShareSecurityService shareSecurityService;
    private final NepaliTemplateService nepaliTemplateService;
    private final NepaliTemplateMapper nepaliTemplateMapper;
    private final InsuranceService insuranceService;
    private final NotificationMasterService notificationMasterService;
    private final CustomerLoanFlagService customerLoanFlagService;
    private final CustomerInfoService customerInfoService;

    public CustomerLoanServiceImpl(
        CustomerLoanRepository customerLoanRepository,
        UserService userService,
        CustomerService customerService,
        CompanyInfoService companyInfoService,
        DmsLoanFileService dmsLoanFileService,
        SiteVisitService siteVisitService,
        FinancialService financialService,
        MawCreditRiskGradingService mawCreditRiskGradingService,
        CreditRiskGradingAlphaService creditRiskGradingAlphaService,
        SecurityService securityservice,
        ProposalService proposalService,
        CustomerOfferService customerOfferService,
        CreditRiskGradingService creditRiskGradingService,
        GroupServices groupService,
        VehicleSecurityService vehicleSecurityService,
        ShareSecurityService shareSecurityService,
        NepaliTemplateService nepaliTemplateService,
        NepaliTemplateMapper nepaliTemplateMapper,
        InsuranceService insuranceService,
        NotificationMasterService notificationMasterService,
        CustomerLoanFlagService customerLoanFlagService,

        CustomerInfoService customerInfoService) {
        this.customerLoanRepository = customerLoanRepository;
        this.userService = userService;
        this.customerService = customerService;
        this.companyInfoService = companyInfoService;
        this.dmsLoanFileService = dmsLoanFileService;
        this.siteVisitService = siteVisitService;
        this.financialService = financialService;
        this.mawCreditRiskGradingService = mawCreditRiskGradingService;
        this.creditRiskGradingAlphaService = creditRiskGradingAlphaService;
        this.securityService = securityservice;
        this.proposalService = proposalService;
        this.customerOfferService = customerOfferService;
        this.creditRiskGradingService = creditRiskGradingService;
        this.groupService = groupService;
        this.vehicleSecurityService = vehicleSecurityService;
        this.shareSecurityService = shareSecurityService;
        this.nepaliTemplateService = nepaliTemplateService;
        this.nepaliTemplateMapper = nepaliTemplateMapper;
        this.insuranceService = insuranceService;
        this.notificationMasterService = notificationMasterService;
        this.customerLoanFlagService = customerLoanFlagService;
        this.customerInfoService = customerInfoService;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public List<CustomerLoan> findAll() {
        return customerLoanRepository.findAll();
    }

    @Override
    public CustomerLoan findOne(Long id) {
        CustomerLoan customerLoan = customerLoanRepository.findById(id).get();
        if (ProductUtils.OFFER_LETTER) {
            CustomerOfferLetter customerOfferLetter = customerOfferService
                .findByCustomerLoanId(customerLoan.getId());
            if (customerOfferLetter != null) {
                CustomerOfferLetterDto customerOfferLetterDto = new CustomerOfferLetterDto();
                BeanUtils.copyProperties(customerOfferLetter, customerOfferLetterDto);
                customerOfferLetterDto.setId(customerOfferLetter.getId());
                customerLoan.setCustomerOfferLetter(customerOfferLetterDto);
            }
        }
        if (ProductUtils.NEP_TEMPLATE) {
            Map<String, String> filterParams = new HashMap<>();
            filterParams.put("customerLoan.id", String.valueOf(id));
            List<NepaliTemplate> nepaliTemplates = nepaliTemplateService
                .findAllBySpec(filterParams);
            if (!nepaliTemplates.isEmpty()) {
                customerLoan
                    .setNepaliTemplates(nepaliTemplateMapper.mapEntitiesToDtos(nepaliTemplates));
            }
        }
        return mapLoanHolderToCustomerLoan(customerLoan);
    }

    @Transactional
    @Override
    public CustomerLoan save(CustomerLoan customerLoan) {
        // validation start
        if (customerLoan.getLoan() == null) {
            throw new ServiceValidationException("Loan can not be null");
        }
        CompanyInfo companyInfo = null;
        boolean isNewLoan = false;
        Customer customer = customerLoan.getCustomerInfo();
        if (customerLoan.getLoanCategory() == LoanApprovalType.BUSINESS_TYPE) {
            companyInfo = customerLoan.getCompanyInfo();
            if (!companyInfo.isValid()) {
                throw new ServiceValidationException(
                    companyInfo.getValidationMsg());
            }
        } else {
            if (!customer.isValid()) {
                throw new ServiceValidationException(
                    customer.getValidationMsg());
            }
        }

        // validation end

        if (customerLoan.getId() == null) {
            isNewLoan = true;
            customerLoan.setBranch(userService.getAuthenticatedUser().getBranch().get(0));
            LoanStage stage = new LoanStage();
            stage.setToRole(userService.getAuthenticatedUser().getRole());
            stage.setFromRole(userService.getAuthenticatedUser().getRole());
            stage.setFromUser(userService.getAuthenticatedUser());
            stage.setToUser(userService.getAuthenticatedUser());
            stage.setComment(DocAction.DRAFT.name());
            stage.setDocAction(DocAction.DRAFT);
            customerLoan.setCurrentStage(stage);
        }

        if (null != companyInfo) {
            customerLoan
                .setCompanyInfo(
                    this.companyInfoService.findOne(customerLoan.getLoanHolder().getAssociateId()));

            /*
            if business loan , business pan/vat number will be citizenship number , companay name will be customer name
            and establishment date will be issue date
             */

//            customer.setCustomerName(companyInfo.getCompanyName());
//            customer.setCitizenshipNumber(companyInfo.getPanNumber());
//            customer.setCitizenshipIssuedDate(companyInfo.getEstablishmentDate());
//            customer.setOccupation(companyInfo.getBusinessType().toString());
//            // look whether customer already exists
//            try {
//                Customer existingCustomer = customerService
//                    .findCustomerByCustomerNameAndCitizenshipNumberAndCitizenshipIssuedDate(
//                        customer.getCustomerName(), customer.getCitizenshipNumber(),
//                        customer.getCitizenshipIssuedDate()
//                    );
//                if (existingCustomer != null) {
//
//                    customer.setId(existingCustomer.getId());
//                }
//            } catch (Exception e) {
//                logger.debug(" No customer {} with pan {} exists", customer.getCustomerName(),
//                    customer.getCitizenshipNumber());
//            }
//

        }

        if (customer != null) {
            Customer c = this.customerService
                .findOne(customerLoan.getLoanHolder().getAssociateId());
            customerLoan.setCustomerInfo(c);
            customerLoan
                .setLoanHolder(customerInfoService.findByAssociateIdAndCustomerType(c.getId(),
                    CustomerType.INDIVIDUAL));
        }

        if (customerLoan.getFinancial() != null) {
            customerLoan.setFinancial(customerLoan.getLoanHolder().getFinancial());
        }
        if (customerLoan.getSecurity() != null) {
            customerLoan.setSecurity(customerLoan.getLoanHolder().getSecurity());
        }
        if (customerLoan.getSiteVisit() != null) {
            customerLoan.setSiteVisit(customerLoan.getLoanHolder().getSiteVisit());
        }
        if (customerLoan.getProposal() != null) {
            customerLoan.setProposal(this.proposalService.save(customerLoan.getProposal()));
        }
        if (customerLoan.getCreditRiskGrading() != null) {
            customerLoan.setCreditRiskGrading(
                creditRiskGradingService.save(customerLoan.getCreditRiskGrading()));
        }
        if (customerLoan.getGroup() != null) {
            customerLoan.setGroup(this.groupService.save(customerLoan.getGroup()));
        }
        if (customerLoan.getVehicleSecurity() != null) {
            customerLoan
                .setVehicleSecurity(vehicleSecurityService.save(customerLoan.getVehicleSecurity()));
        }
        if (customerLoan.getMawCreditRiskGrading() != null) {
            customerLoan.setMawCreditRiskGrading(
                this.mawCreditRiskGradingService.save(customerLoan.getMawCreditRiskGrading()));
        }
        if (customerLoan.getCreditRiskGradingAlpha() != null) {
            customerLoan.setCreditRiskGradingAlpha(
                this.creditRiskGradingAlphaService.save(customerLoan.getCreditRiskGradingAlpha()));
        }
        if (customerLoan.getShareSecurity() != null) {
            customerLoan
                .setShareSecurity(customerLoan.getLoanHolder().getShareSecurity());
        }
        if (customerLoan.getInsurance() != null) {
            customerLoan.setInsurance(customerLoan.getLoanHolder().getInsurance());
        }

        CustomerLoan savedCustomerLoan = customerLoanRepository.save(customerLoan);
        postLoanConditionCheck(savedCustomerLoan);

        if (!customerLoan.getNepaliTemplates().isEmpty()) {
            List<NepaliTemplate> nepaliTemplates = nepaliTemplateMapper
                .mapDtosToEntities(customerLoan.getNepaliTemplates());
            nepaliTemplates.forEach(v -> v.setCustomerLoan(savedCustomerLoan));
            nepaliTemplateService.saveAll(nepaliTemplates);
        }

        if (isNewLoan) {

            String refNumber = new StringBuilder().append(LocalDate.now().getYear())
                .append(LocalDate.now().getMonthValue())
                .append(LocalDate.now().getDayOfMonth()).append(SEPERATOR_FRONT_SLASH)
                .append(customerLoan.getLoan().getId()).append(SEPERATOR_FRONT_SLASH)
                .append(
                    StringUtil.getAcronym(customerLoan.getLoanCategory().name(), SEPERATOR_BLANK))
                .append(SEPERATOR_FRONT_SLASH).append(customerLoan.getId()).toString();

            customerLoanRepository.updateReferenceNo(refNumber, customerLoan.getId());
        }

        return savedCustomerLoan;
    }


    @Override
    public Page<CustomerLoan> findAllPageable(Object t, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(t, Map.class);
        User u = userService.getAuthenticatedUser();
        String branchAccess = userService.getRoleAccessFilterByBranch().stream()
            .map(Object::toString).collect(Collectors.joining(","));
        if (s.containsKey("branchIds")) {
            branchAccess = s.get("branchIds");
        }
        s.put("currentUserRole", u.getRole() == null ? null : u.getRole().getId().toString());
        s.put("toUser", u == null ? null : u.getId().toString());
        s.put("branchIds", branchAccess);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        return customerLoanRepository.findAll(specification, pageable);
    }

    @Override
    public List<CustomerLoan> findAll(Object search) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(search, Map.class);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        return customerLoanRepository.findAll(specification);
    }

    @Override
    public List<CustomerLoan> saveAll(List<CustomerLoan> list) {
        return customerLoanRepository.saveAll(list);
    }

    @Override
    public void sendForwardBackwardLoan(CustomerLoan customerLoan) {
        if (customerLoan.getCurrentStage() == null
            || customerLoan.getCurrentStage().getToRole() == null
            || customerLoan.getCurrentStage().getToUser() == null) {
            logger.warn("Empty current Stage{}", customerLoan.getCurrentStage());
            throw new ServiceValidationException("Unable to perform Task");
        }
        customerLoanRepository.save(customerLoan);
    }

    @Override
    public void sendForwardBackwardLoan(List<CustomerLoan> customerLoans) {
        customerLoans.forEach(this::sendForwardBackwardLoan);
    }

    @Override
    public Map<String, Integer> statusCount() {
        User u = userService.getAuthenticatedUser();
        List<Long> branchAccess = userService.getRoleAccessFilterByBranch();
        return customerLoanRepository.statusCount(u.getRole().getId(), branchAccess, u.getId());
    }

    @Override
    public List<CustomerLoan> getFirst5CustomerLoanByDocumentStatus(DocStatus status) {
        User u = userService.getAuthenticatedUser();
        return customerLoanRepository
            .findFirst5ByDocumentStatusAndCurrentStageToRoleIdAndBranchIdOrderByIdDesc(status,
                u.getRole().getId(), u.getBranch().get(0).getId());
    }

    @Override
    public List<PieChartDto> proposedAmount(String startDate, String endDate)
        throws ParseException {
        List<Long> branchAccess = userService.getRoleAccessFilterByBranch();
        List<PieChartDto> data = new ArrayList<>();
        if ((startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty())) {
            data = customerLoanRepository.proposedAmount(branchAccess);
        } else if (startDate == null || startDate.isEmpty()) {
            data = customerLoanRepository.proposedAmountBefore(branchAccess,
                new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
        } else if (endDate == null || endDate.isEmpty()) {
            data = customerLoanRepository.proposedAmountAfter(branchAccess, new SimpleDateFormat(
                "MM/dd/yyyy").parse(startDate));
        } else {
            data = customerLoanRepository.proposedAmountBetween(branchAccess, new SimpleDateFormat(
                "MM/dd/yyyy").parse(startDate), new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
        }
        return data;
    }

    @Override
    public List<PieChartDto> proposedAmountByBranch(Long branchId, String startDate,
        String endDate) throws ParseException {
        List<PieChartDto> data = new ArrayList<>();
        if ((startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty())) {
            data = customerLoanRepository.proposedAmountByBranchId(branchId);
        } else if (startDate == null || startDate.isEmpty()) {
            data = customerLoanRepository.proposedAmountByBranchIdAndDateBefore(branchId,
                new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
        } else if (endDate == null || endDate.isEmpty()) {
            data = customerLoanRepository.proposedAmountByBranchIdAndDateAfter(branchId,
                new SimpleDateFormat(
                    "MM/dd/yyyy").parse(startDate));
        } else {
            data = customerLoanRepository.proposedAmountByBranchIdAndDateBetween(branchId,
                new SimpleDateFormat(
                    "MM/dd/yyyy").parse(startDate),
                new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
        }
        return data;
    }

    @Override
    public List<CustomerLoan> getByCitizenshipNumber(String citizenshipNumber) {
        return customerLoanRepository
            .getByCustomerInfoCitizenshipNumber(citizenshipNumber);
    }

    @Override
    public Page<CustomerLoan> getCatalogues(Object searchDto, Pageable pageable) {
        final ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
        String branchAccess = userService.getRoleAccessFilterByBranch().stream()
            .map(Object::toString).collect(Collectors.joining(","));
        if (s.containsKey("branchIds")) {
            branchAccess = s.get("branchIds");
        }
        s.put("branchIds", branchAccess);
        s.values().removeIf(Objects::isNull);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        return customerLoanRepository.findAll(specification, pageable);
    }

    @Override
    public Page<CustomerLoan> getCommitteePull(Object searchDto, Pageable pageable) {
        final ObjectMapper objectMapper = new ObjectMapper();
        User u = userService.getAuthenticatedUser();
        if (u.getRole().getRoleType().equals(RoleType.COMMITTEE)) {
            Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
            String branchAccess = userService.getRoleAccessFilterByBranch().stream()
                .map(Object::toString).collect(Collectors.joining(","));
            if (s.containsKey("branchIds")) {
                branchAccess = s.get("branchIds");
            }
            s.put("branchIds", branchAccess);
            s.put("currentUserRole", u.getRole().getId().toString());
            s.put("toUser",
                userService.findByRoleIdAndIsDefaultCommittee(u.getRole().getId(), true).get(0)
                    .getId()
                    .toString());
            s.values().removeIf(Objects::isNull);
            logger.info("query for pull {}", s);
            final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
            final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
            Page<CustomerLoan> customerLoanList = customerLoanRepository
                .findAll(specification, pageable);
            for (CustomerLoan c : customerLoanList.getContent()) {
                for (LoanStageDto l : c.getPreviousList()) {
                    if (l.getToUser().getId() == (u.getId())) {
                        c.setPulled(true);
                        break;
                    }
                }
            }
            return customerLoanList;
        }
        return null;
    }

    @Override
    public Page<CustomerLoan> getIssuedOfferLetter(Object searchDto, Pageable pageable) {
        final ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
        String branchAccess = userService.getRoleAccessFilterByBranch().stream()
            .map(Object::toString).collect(Collectors.joining(","));
        if (s.containsKey("branchIds")) {
            branchAccess = s.get("branchIds");
        }
        s.put("branchIds", branchAccess);
        s.put("documentStatus", DocStatus.APPROVED.name());
        s.put("currentOfferLetterStage",
            String.valueOf(userService.getAuthenticatedUser().getId()));
        s.values().removeIf(Objects::isNull);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        return customerLoanRepository.findAll(specification, pageable);
    }

    @Override
    public CustomerLoan delCustomerLoan(Long id) {
        Optional<CustomerLoan> customerLoan = customerLoanRepository.findById(id);
        if (!customerLoan.isPresent()) {
            logger.info("No Loan Found of Id {}", id);
            throw new ServiceValidationException("No Loan Found of Id " + id);
        }
        if (!customerLoan.get().getCurrentStage().getDocAction().equals(DocAction.DRAFT)) {
            logger.info("Loan can not be deleted it is in stage", id);
            throw new ServiceValidationException("Loan can not be deleted it is in stage");
        }
        User u = userService.getAuthenticatedUser();
        if (u.getRole().getRoleType().equals(RoleType.MAKER)) {
            customerLoanRepository
                .deleteByIdAndCurrentStageDocAction(id, DocAction.DRAFT);
            if (!customerLoanRepository.findById(id).isPresent()) {
                return new CustomerLoan();
            } else {
                throw new ServiceValidationException("Oops Something went wrong");
            }
        } else {
            throw new ServiceValidationException("You don't have access to delete file");
        }
    }

    @Override
    public List<StatisticDto> getStats(Long branchId, String startDate, String endDate)
        throws ParseException {
        List<StatisticDto> statistics = new ArrayList<>();
        logger.debug("Request to get the statistics about the existing loans.");
        return getLasStatistics(branchId, startDate, endDate);
    }

    @Override
    public Map<String, String> chkUserContainCustomerLoan(Long id) {
        User u = userService.findOne(id);
        Integer count = customerLoanRepository
            .chkUserContainCustomerLoan(id, u.getRole().getId(), DocStatus.PENDING);
        Map<String, String> map = new HashMap<>();
        map.put("count", String.valueOf(count));
        map.put("status", count == 0 ? "false" : "true");
        return map;
    }


    private List<StatisticDto> getLasStatistics(Long branchId, String startDate, String endDate)
        throws ParseException {
        List<StatisticDto> data = new ArrayList<>();
        if (branchId == 0) {
            List<Long> branches = userService.getRoleAccessFilterByBranch();
            if ((startDate == null || startDate.isEmpty()) && (endDate == null || endDate
                .isEmpty())) {
                data = customerLoanRepository.getLasStatistics(branches);
            } else if (startDate == null || startDate.isEmpty()) {
                data = customerLoanRepository.getLasStatisticsAndDateBefore(branches,
                    new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
            } else if (endDate == null || endDate.isEmpty()) {
                data = customerLoanRepository
                    .getLasStatisticsAndDateAfter(branches, new SimpleDateFormat(
                        "MM/dd/yyyy").parse(startDate));
            } else {
                data = customerLoanRepository
                    .getLasStatisticsAndDateBetween(branches, new SimpleDateFormat(
                            "MM/dd/yyyy").parse(startDate),
                        new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
            }
        } else {
            if ((startDate == null || startDate.isEmpty()) && (endDate == null || endDate
                .isEmpty())) {
                data = customerLoanRepository.getLasStatisticsByBranchId(branchId);
            } else if (startDate == null || startDate.isEmpty()) {
                data = customerLoanRepository.getLasStatisticsByBranchIdAndDateBefore(branchId,
                    new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
            } else if (endDate == null || endDate.isEmpty()) {
                data = customerLoanRepository.getLasStatisticsByBranchIdAndDateAfter(branchId,
                    new SimpleDateFormat(
                        "MM/dd/yyyy").parse(startDate));
            } else {
                data = customerLoanRepository.getLasStatisticsByBranchIdAndDateBetween(branchId,
                    new SimpleDateFormat(
                        "MM/dd/yyyy").parse(startDate),
                    new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
            }

        }
        return data;
    }

    @Override
    public CustomerLoan renewCloseEntity(CustomerLoan previousLoan) {
        final Long tempParentId = previousLoan.getId();
        previousLoan.setParentId(tempParentId);
        previousLoan.setId(null);
        previousLoan.setDocumentStatus(DocStatus.PENDING);

        if (previousLoan.getDmsLoanFile() != null) {
            previousLoan.getDmsLoanFile().setId(null);
            previousLoan.setDmsLoanFile(dmsLoanFileService.save(previousLoan.getDmsLoanFile()));
        }
        if (previousLoan.getFinancial() != null) {
            previousLoan.getFinancial().setId(null);
            previousLoan.setFinancial(financialService.save(previousLoan.getFinancial()));
        }
        if (previousLoan.getSecurity() != null) {
            previousLoan.getSecurity().setId(null);
            previousLoan.setSecurity(securityService.save(previousLoan.getSecurity()));
        }
        if (previousLoan.getSiteVisit() != null) {
            previousLoan.getSiteVisit().setId(null);
        }
        if (previousLoan.getProposal() != null) {
            previousLoan.getProposal().setId(null);
            previousLoan.setProposal(proposalService.save(previousLoan.getProposal()));
        }
        if (previousLoan.getCreditRiskGrading() != null) {
            previousLoan.getCreditRiskGrading().setId(null);
            previousLoan
                .setCreditRiskGrading(
                    creditRiskGradingService.save(previousLoan.getCreditRiskGrading()));
        }
        if (previousLoan.getGroup() != null) {
            previousLoan.getGroup().setId(null);
            previousLoan.setGroup(groupService.save(previousLoan.getGroup()));
        }
        if (previousLoan.getVehicleSecurity() != null) {
            previousLoan.getVehicleSecurity().setId(null);
            previousLoan
                .setVehicleSecurity(vehicleSecurityService.save(previousLoan.getVehicleSecurity()));
        }
        if (previousLoan.getMawCreditRiskGrading() != null) {
            previousLoan.getMawCreditRiskGrading().setId(null);
            previousLoan.setMawCreditRiskGrading(
                mawCreditRiskGradingService.save(previousLoan.getMawCreditRiskGrading()));
        }
        if (previousLoan.getCreditRiskGradingAlpha() != null) {
            previousLoan.getCreditRiskGradingAlpha().setId(null);
            previousLoan.setCreditRiskGradingAlpha(
                creditRiskGradingAlphaService.save(previousLoan.getCreditRiskGradingAlpha()));
        }
        if (previousLoan.getShareSecurity() != null) {
            previousLoan.getShareSecurity().setId(null);
            previousLoan
                .setShareSecurity(this.shareSecurityService.save(previousLoan.getShareSecurity()));
        }
        if (previousLoan.getInsurance() != null) {
            previousLoan.getInsurance().forEach(value -> value.setId(null));
            previousLoan.setInsurance(this.insuranceService.saveAll(previousLoan.getInsurance()));
        }

        LoanStage stage = new LoanStage();
        stage.setToRole(userService.getAuthenticatedUser().getRole());
        stage.setFromRole(userService.getAuthenticatedUser().getRole());
        stage.setFromUser(userService.getAuthenticatedUser());
        stage.setToUser(userService.getAuthenticatedUser());
        stage.setComment(DocAction.DRAFT.name());
        stage.setDocAction(DocAction.DRAFT);
        previousLoan.setCurrentStage(stage);
        previousLoan.setPreviousList(null);
        previousLoan.setPreviousStageList(null);
        previousLoan.setCustomerDocument(null);
        CustomerLoan customerLoan = customerLoanRepository.save(previousLoan);
        customerLoanRepository.updateCloseRenewChildId(customerLoan.getId(), tempParentId);
        return customerLoan;
    }

    @Override
    public String csv(Object searchDto) {
        Report report = ReportFactory.getReport(populate(Optional.of(searchDto)));
        return getDownloadPath() + report.getFileName();

    }

    @Override
    public List<CustomerLoan> getLoanByCustomerId(Long id) {
        return customerLoanRepository.getCustomerLoanByCustomerInfoId(id);
    }

    @Override
    public List<CustomerLoan> getLoanByLoanHolderId(Long id) {
        return customerLoanRepository.getCustomerLoanByAndLoanHolderId(id);
    }

    @Override
    public List<CustomerLoan> getLoanByCustomerKycGroup(CustomerRelative customerRelative) {
        String date = new SimpleDateFormat("yyyy-MM-dd")
            .format(customerRelative.getCitizenshipIssuedDate());
        Map<String, String> map = new HashMap<>();
        map.put("customerRelativeName", customerRelative.getCustomerRelativeName());
        map.put("citizenshipNumber", customerRelative.getCitizenshipNumber());
        map.put("citizenshipIssuedDate", date);
        map.values().removeIf(Objects::isNull);
        logger.info("get loan by kyc search parm{}", map);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(
            map);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        return customerLoanRepository.findAll(specification);

    }

    @Override
    public Object getLoanByCustomerGroup(CustomerGroup customerGroup) {
        Map<String, String> map = new HashMap<>();
        map.put("groupCode", customerGroup.getGroupCode());
        map.values().removeIf(Objects::isNull);
        logger.info("get loan by kyc search parm{}", map);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(
            map);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        List<CustomerLoan> customerLoans = customerLoanRepository.findAll(specification);
        Map<String, CustomerLoanGroupDto> filterList = new HashMap<>();

        customerLoans.forEach(customerLoan -> {
            if (!filterList.containsKey(String.valueOf(customerLoan.getCustomerInfo().getId()))) {
                CustomerInfo customerInfo = customerLoan.getLoanHolder();
                List<BigDecimal> l = customerLoans.stream()
                    .filter(c -> Objects.equals(c.getCustomerInfo().getId(),
                        customerLoan.getCustomerInfo().getId()) && c.getProposal() != null)
                    .map(c -> c.getProposal().getProposedLimit()).
                        collect(Collectors.toList());
                BigDecimal totalLimit = l.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                CustomerLoanGroupDto customerLoanGroupDto = new CustomerLoanGroupDto();
                customerLoanGroupDto.setLoanHolder(customerLoan.getLoanHolder());
                customerLoanGroupDto.setTotalObtainedLimit(totalLimit);
                filterList.put(String.valueOf(customerInfo.getId()), customerLoanGroupDto);
            }
        });

        return filterList.values();

    }

    @Override
    public Page<Customer> getCustomerFromCustomerLoan(Object searchDto, Pageable pageable) {
        final ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
        s.put("distinctByCustomer", "true");
        s.values().removeIf(Objects::isNull);
        logger.info("search param for customer in customerLoan {}", s);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        Page<CustomerLoan> customerLoanPage = customerLoanRepository
            .findAll(specification, pageable);
        List<Customer> customerList = new ArrayList<>();
        customerLoanPage.getContent().forEach(customerLoan -> {
            if (!customerList.contains(customerLoan) && (customerLoan.getCustomerInfo() != null)) {
                customerList.add(customerLoan.getCustomerInfo());
            }
        });
        List<Customer> finalList = customerList.stream().filter(distinctByKey(Customer::getId))
            .collect(
                Collectors.toList());

        Page<Customer> pages = new PageImpl<Customer>(finalList, pageable,
            customerLoanPage.getTotalElements());
        return pages;
    }

    @Override
    public List<CustomerLoan> getLoanByCustomerGuarantor(Guarantor guarantor) {
        String date = new SimpleDateFormat("yyyy-MM-dd")
            .format(guarantor.getIssuedYear());
        Map<String, String> map = new HashMap<>();
        map.put("guarantorName", guarantor.getName());
        map.put("guarantorCitizenshipNumber", guarantor.getCitizenNumber());
        map.put("guarantorCitizenshipIssuedDate", date);
        map.put("guarantorDistrictId", String.valueOf(guarantor.getDistrict().getId()));
        map.put("guarantorProvinceId", String.valueOf(guarantor.getProvince().getId()));
        map.values().removeIf(Objects::isNull);
        logger.info("get loan by guarantor search parm{}", map);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(
            map);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        return customerLoanRepository.findAll(specification);
    }

    public long calculateLoanSpanAndPossession(Date lastModifiedDate, Date createdLastDate) {
        int daysdiff = 0;
        Date lastModifiedAt = lastModifiedDate;
        Date createdLastAt = createdLastDate;

        long differenceInDate = lastModifiedAt.getTime() - createdLastAt.getTime();
        long diffInDays = TimeUnit.DAYS.convert(differenceInDate, TimeUnit.MILLISECONDS);
        daysdiff = (int) diffInDays;

        return daysdiff;

    }

    public long calculatePendingLoanSpanAndPossession(Date createdDate) {
        int daysDiff = 0;
        Date createdAt = createdDate;
        Date currentDate = new Date();

        long differenceInDate = currentDate.getTime() - createdAt.getTime();
        long diffInDays = TimeUnit.DAYS.convert(differenceInDate, TimeUnit.MILLISECONDS);
        daysDiff = (int) diffInDays;

        return daysDiff;

    }

    public String formatCsvDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * Checks `proposed limit`, `share considered value` before saving loan.
     *
     * @param loan An instance of Customer Loan.
     */
    @Override
    public void postLoanConditionCheck(CustomerLoan loan) {
        // check if proposed amount is equal to ZERO
        if (loan.getProposal() != null) {
            CustomerLoanFlag customerLoanFlag = loan.getLoanFlags().stream()
                .filter(loanFlag -> loanFlag.getFlag().equals(LoanFlag.ZERO_PROPOSAL_AMOUNT))
                .collect(CustomerLoanFlag.toSingleton());

            boolean flag = loan.getProposal().getProposedLimit().compareTo(BigDecimal.ZERO) <= 0;
            if (flag && customerLoanFlag == null) {
                customerLoanFlag = new CustomerLoanFlag();
                customerLoanFlag.setCustomerLoan(loan);
                customerLoanFlag.setFlag(LoanFlag.ZERO_PROPOSAL_AMOUNT);
                customerLoanFlag.setDescription(LoanFlag.ZERO_PROPOSAL_AMOUNT.getValue()[1]);
                customerLoanFlag
                    .setOrder(Integer.parseInt(LoanFlag.ZERO_PROPOSAL_AMOUNT.getValue()[0]));
                customerLoanFlagService.save(customerLoanFlag);
            } else if (!flag && customerLoanFlag != null) {
                customerLoanFlagService.deleteCustomerLoanFlagById(customerLoanFlag.getId());
            }
        }
        // check if company VAT/PAN registration will expire
        if (loan.getCompanyInfo() != null) {
            CustomerLoanFlag customerLoanFlag = loan.getLoanFlags().stream()
                .filter(loanFlag -> loanFlag.getFlag().equals(LoanFlag.COMPANY_VAT_PAN_EXPIRY))
                .collect(CustomerLoanFlag.toSingleton());

            Map<String, String> insuranceFilter = new HashMap<>();
            insuranceFilter.put(NotificationMasterSpec.FILTER_BY_NOTIFICATION_KEY,
                NotificationMasterType.COMPANY_REGISTRATION_EXPIRY_BEFORE.toString());
            NotificationMaster notificationMaster = notificationMasterService
                .findOneBySpec(insuranceFilter).orElse(null);
            if (notificationMaster != null) {
                try {
                    int daysToExpiryBefore = notificationMaster.getValue();
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.DAY_OF_MONTH, daysToExpiryBefore);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.MM_DD_YYYY);
                    Date today = dateFormat.parse(dateFormat.format(c.getTime()));
                    Date expiry = dateFormat.parse(dateFormat.format(
                        loan.getCompanyInfo().getLegalStatus().getRegistrationExpiryDate()
                    ));
                    boolean flag = expiry.before(today);
                    if (flag && customerLoanFlag == null) {
                        customerLoanFlag = new CustomerLoanFlag();
                        customerLoanFlag.setCustomerLoan(loan);
                        customerLoanFlag.setFlag(LoanFlag.COMPANY_VAT_PAN_EXPIRY);
                        customerLoanFlag.setDescription(String
                            .format(LoanFlag.COMPANY_VAT_PAN_EXPIRY.getValue()[1],
                                daysToExpiryBefore));
                        customerLoanFlag.setOrder(
                            Integer.parseInt(LoanFlag.COMPANY_VAT_PAN_EXPIRY.getValue()[0]));
                        customerLoanFlagService.save(customerLoanFlag);
                    } else if (!flag && customerLoanFlag != null) {
                        customerLoanFlagService
                            .deleteCustomerLoanFlagById(customerLoanFlag.getId());
                    }
                } catch (ParseException e) {
                    logger.error("Error parsing company registration expiry date");
                }
            }
        }

        // check proposed limit VS considered amount
        ShareSecurity shareSecurity = loan.getShareSecurity();
        if (shareSecurity != null) {
            shareSecurityService.execute(Optional.ofNullable(loan.getId()));
        }
        // insurance expiry verification
        List<Insurance> insurance = loan.getInsurance();
        if (insurance != null) {
            insuranceService.execute(Optional.ofNullable(loan.getId()));
        }
    }

    @Override
    public List<CustomerLoan> findAllBySpec(Map<String, String> filterParams) {
        CustomerLoanSpecBuilder builder = new CustomerLoanSpecBuilder(filterParams);
        return customerLoanRepository.findAll(builder.build());
    }

    @Override
    public List<CustomerLoan> findByCombinedLoanId(long id) {
        return customerLoanRepository.findAllByCombinedLoanId(id);
    }

    @Override
    public String title() {
        return "Form Report For Loan";
    }

    @Override
    public List<AbstractColumn> columns() {
        AbstractColumn columnBranch = ColumnBuilder.getNew()
            .setColumnProperty("branch.name", String.class.getName())
            .setTitle("Branch").setWidth(85)
            .build();

        AbstractColumn columnName = ColumnBuilder.getNew()
            .setColumnProperty("customerInfo.customerName", String.class.getName())
            .setTitle("Name").setWidth(100)
            .build();

        AbstractColumn columnLoanName = ColumnBuilder.getNew()
            .setColumnProperty("loan.name", String.class.getName())
            .setTitle("Loan Name").setWidth(85)
            .build();

        AbstractColumn columnCurrentPosition = ColumnBuilder.getNew()
            .setColumnProperty("toUser.name", String.class.getName())
            .setTitle("Current Position").setWidth(85)
            .build();
        AbstractColumn columnDesignation = ColumnBuilder.getNew()
            .setColumnProperty("toRole.roleName", String.class.getName())
            .setTitle("Designation").setWidth(85)
            .build();
        AbstractColumn columnCreatedAt = ColumnBuilder.getNew()
            .setColumnProperty("createdAt", String.class.getName())
            .setTitle("Created At").setWidth(85)
            .build();
        AbstractColumn columnCompanyName = ColumnBuilder.getNew()
            .setColumnProperty("companyInfo.companyName", String.class.getName())
            .setTitle("Company Name").setWidth(100)
            .build();
        AbstractColumn columnLoanPendingSpan = ColumnBuilder.getNew()
            .setColumnProperty("loanPendingSpan", Long.class.getName())
            .setTitle("Loan Pending Span").setWidth(80)
            .build();
        AbstractColumn columnProposedAmount = ColumnBuilder.getNew()
            .setColumnProperty("proposal.proposedLimit", BigDecimal.class.getName())
            .setTitle("Proposed Amount").setWidth(85)
            .build();
        AbstractColumn columnPossessionUnderDays = ColumnBuilder.getNew()
            .setColumnProperty("loanPossession", Long.class.getName())
            .setTitle("Possession Under Days").setWidth(80)
            .build();
        AbstractColumn columnLifeSpan = ColumnBuilder.getNew()
            .setColumnProperty("loanSpan", Long.class.getName())
            .setTitle("Loan Life span").setWidth(80)
            .build();
        AbstractColumn columnTypes = ColumnBuilder.getNew()
            .setColumnProperty("loanType", LoanType.class.getName())
            .setTitle("Types").setWidth(85)
            .build();
        AbstractColumn columnLoanCategory = ColumnBuilder.getNew()
            .setColumnProperty("loanCategory", LoanApprovalType.class.getName())
            .setTitle("Loan Category").setWidth(85)
            .build();
        AbstractColumn columnStatus = ColumnBuilder.getNew()
            .setColumnProperty("documentStatus", DocStatus.class.getName())
            .setTitle("Status").setWidth(85)
            .build();

        return Arrays.asList(columnBranch, columnName, columnCompanyName, columnLoanName,
            columnProposedAmount, columnTypes, columnLoanCategory, columnStatus,
            columnCurrentPosition, columnDesignation,
            columnCreatedAt, columnLoanPendingSpan, columnLifeSpan, columnPossessionUnderDays);
    }


    @Override
    public ReportParam populate(Optional optional) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final User u = userService.getAuthenticatedUser();
        Map<String, String> s = objectMapper.convertValue(optional.get(), Map.class);
        String branchAccess = userService.getRoleAccessFilterByBranch().stream()
            .map(Object::toString).collect(Collectors.joining(","));
        if (s.containsKey("branchIds")) {
            branchAccess = s.get("branchIds");
        }
        s.put("branchIds", branchAccess);
        boolean isPullCsv = false;
        if (s.get("committee") != null) {
            isPullCsv = true;
        }
        if (u.getRole().getRoleType().equals(RoleType.COMMITTEE) && isPullCsv) {
            s.put("currentUserRole", u.getRole().getId().toString());
            s.put("toUser",
                userService.findByRoleIdAndIsDefaultCommittee(u.getRole().getId(), true).get(0)
                    .getId()
                    .toString());
        }
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        final List<CustomerLoan> customerLoanList = customerLoanRepository.findAll(specification);
        List csvDto = new ArrayList();
        for (CustomerLoan c : customerLoanList) {
            CustomerLoanCsvDto customerLoanCsvDto = new CustomerLoanCsvDto();
            customerLoanCsvDto.setBranch(c.getBranch());
            customerLoanCsvDto.setCustomerInfo(c.getCustomerInfo());
            customerLoanCsvDto.setCompanyInfo(c.getCompanyInfo());
            customerLoanCsvDto.setLoan(c.getLoan());
            customerLoanCsvDto.setProposal(c.getProposal());
            customerLoanCsvDto.setLoanType(c.getLoanType());
            customerLoanCsvDto.setLoanCategory(c.getLoanCategory());
            customerLoanCsvDto.setDocumentStatus(c.getDocumentStatus());
            customerLoanCsvDto.setToUser(c.getCurrentStage().getToUser());
            customerLoanCsvDto.setToRole(c.getCurrentStage().getToRole());
            customerLoanCsvDto.setCreatedAt(formatCsvDate(c.getCurrentStage().getCreatedAt()));
            customerLoanCsvDto.setCurrentStage(c.getCurrentStage());
            if (c.getDocumentStatus() == DocStatus.PENDING
                || c.getDocumentStatus() == DocStatus.DOCUMENTATION
                || c.getDocumentStatus() == DocStatus.VALUATION
                || c.getDocumentStatus() == DocStatus.UNDER_REVIEW
                || c.getDocumentStatus() == DocStatus.DISCUSSION) {
                customerLoanCsvDto.setLoanPendingSpan(
                    this.calculatePendingLoanSpanAndPossession(c.getCurrentStage().getCreatedAt()));
                customerLoanCsvDto.setLoanPossession(
                    this.calculatePendingLoanSpanAndPossession(
                        c.getCurrentStage().getLastModifiedAt()));
            } else {
                customerLoanCsvDto.setLoanPossession(
                    this.calculateLoanSpanAndPossession(c.getCurrentStage().getLastModifiedAt(),
                        c.getPreviousList().get(c.getPreviousList().size() - 1)
                            .getLastModifiedAt()));
                customerLoanCsvDto.setLoanSpan(
                    this.calculateLoanSpanAndPossession(c.getCurrentStage().getLastModifiedAt(),
                        c.getCurrentStage().getCreatedAt()));
            }

            csvDto.add(customerLoanCsvDto);

        }

        String filePath = getDownloadPath();
        ReportParam reportParam = ReportParam.builder().reportName("Catalogue Report")
            .title(title())
            .subTitle(subTitle()).columns(columns()).data(csvDto).reportType(ReportType.FORM_REPORT)
            .filePath(UploadDir.WINDOWS_PATH + filePath).exportType(ExportType.XLS)
            .build();
        return reportParam;
    }

    public String getDownloadPath() {
        return new PathBuilder(UploadDir.initialDocument)
            .buildBuildFormDownloadPath("Catalogue");
    }

    private CustomerLoan mapLoanHolderToCustomerLoan(CustomerLoan customerLoan) {
        CustomerInfo customerInfo = customerLoan.getLoanHolder();
        customerLoan.setSecurity(customerInfo.getSecurity());
        customerLoan.setFinancial(customerInfo.getFinancial());
        customerLoan.setSiteVisit(customerInfo.getSiteVisit());
        customerLoan.setGuarantor(customerInfo.getGuarantors());
        customerLoan.setInsurance(customerInfo.getInsurance());
        customerLoan.setShareSecurity(customerInfo.getShareSecurity());
        if (customerInfo.getCustomerType().equals(CustomerType.COMPANY)) {
            customerLoan.setCompanyInfo(
                companyInfoService.findOne(customerLoan.getLoanHolder().getAssociateId()));
        } else {
            customerLoan.setCustomerInfo(customerService.findOne(customerInfo.getAssociateId()));
        }

        return customerLoan;
    }
}


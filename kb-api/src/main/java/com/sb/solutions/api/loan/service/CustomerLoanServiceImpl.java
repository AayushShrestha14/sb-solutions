package com.sb.solutions.api.loan.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.approvallimit.emuns.LoanApprovalType;
import com.sb.solutions.api.companyInfo.model.service.CompanyInfoService;
import com.sb.solutions.api.creditRiskGrading.service.CreditRiskGradingService;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.service.CustomerService;
import com.sb.solutions.api.customerRelative.entity.CustomerRelative;
import com.sb.solutions.api.dms.dmsloanfile.service.DmsLoanFileService;
import com.sb.solutions.api.financial.service.FinancialService;
import com.sb.solutions.api.group.service.GroupServices;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.PieChartDto;
import com.sb.solutions.api.loan.StatisticDto;
import com.sb.solutions.api.loan.dto.CustomerLoanCsvDto;
import com.sb.solutions.api.loan.dto.CustomerOfferLetterDto;
import com.sb.solutions.api.loan.dto.LoanStageDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.entity.CustomerOfferLetter;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.productMode.entity.ProductMode;
import com.sb.solutions.api.productMode.service.ProductModeService;
import com.sb.solutions.api.proposal.service.ProposalService;
import com.sb.solutions.api.security.service.SecurityService;
import com.sb.solutions.api.siteVisit.service.SiteVisitService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.api.vehiclesecurity.service.VehicleSecurityService;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.Product;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.csv.CsvMaker;


/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Service
public class CustomerLoanServiceImpl implements CustomerLoanService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoanServiceImpl.class);
    private final CustomerLoanRepository customerLoanRepository;
    private final UserService userService;
    private final ProductModeService productModeService;
    private final CustomerService customerService;
    private final DmsLoanFileService dmsLoanFileService;
    private final CompanyInfoService companyInfoService;
    private final SiteVisitService siteVisitService;
    private final FinancialService financialService;
    private final SecurityService securityService;
    private final ProposalService proposalService;
    private final GroupServices groupService;
    private final CustomerOfferService customerOfferService;
    private final CreditRiskGradingService creditRiskGradingService;
    private final VehicleSecurityService vehicleSecurityService;

    public CustomerLoanServiceImpl(
        CustomerLoanRepository customerLoanRepository,
        UserService userService,
        CustomerService customerService,
        CompanyInfoService companyInfoService,
        DmsLoanFileService dmsLoanFileService,
        SiteVisitService siteVisitService,
        FinancialService financialService,
        SecurityService securityservice,
        ProposalService proposalService,
        ProductModeService productModeService,
        CustomerOfferService customerOfferService,
        CreditRiskGradingService creditRiskGradingService,
        GroupServices groupService,
        VehicleSecurityService vehicleSecurityService
    ) {
        this.customerLoanRepository = customerLoanRepository;
        this.userService = userService;
        this.productModeService = productModeService;
        this.customerService = customerService;
        this.companyInfoService = companyInfoService;
        this.dmsLoanFileService = dmsLoanFileService;
        this.siteVisitService = siteVisitService;
        this.financialService = financialService;
        this.securityService = securityservice;
        this.proposalService = proposalService;
        this.customerOfferService = customerOfferService;
        this.creditRiskGradingService = creditRiskGradingService;
        this.groupService = groupService;
        this.vehicleSecurityService = vehicleSecurityService;
    }

    @Override
    public List<CustomerLoan> findAll() {
        return customerLoanRepository.findAll();
    }

    @Override
    public CustomerLoan findOne(Long id) {
        CustomerLoan customerLoan = customerLoanRepository.findById(id).get();

        CustomerOfferLetter customerOfferLetter = customerOfferService
            .findByCustomerLoanId(customerLoan.getId());
        if (customerOfferLetter != null) {
            CustomerOfferLetterDto customerOfferLetterDto = new CustomerOfferLetterDto();
            BeanUtils.copyProperties(customerOfferLetter, customerOfferLetterDto);
            customerOfferLetterDto.setId(customerOfferLetter.getId());
            customerLoan.setCustomerOfferLetter(customerOfferLetterDto);
        }
        return customerLoan;
    }

    @Override
    public CustomerLoan save(CustomerLoan customerLoan) {
        if (customerLoan.getLoan() == null) {
            throw new ServiceValidationException("Loan can not be null");
        }

        if (customerLoan.getId() == null) {
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

        if (customerLoan.getDmsLoanFile() != null) {
            customerLoan.getDmsLoanFile()
                .setDocumentPath(new Gson().toJson(customerLoan.getDmsLoanFile().getDocumentMap()));
            customerLoan.getDmsLoanFile().setCreatedAt(new Date());
        }
        if (customerLoan.getDmsLoanFile() != null) {
            customerLoan.setDmsLoanFile(dmsLoanFileService.save(customerLoan.getDmsLoanFile()));
        }

        if (customerLoan.getCustomerInfo() != null) {
            customerLoan.setCustomerInfo(this.customerService.save(customerLoan.getCustomerInfo()));
        }
        if (customerLoan.getCompanyInfo() != null
            && customerLoan.getLoanCategory() == LoanApprovalType.BUSINESS_TYPE) {
            customerLoan
                .setCompanyInfo(this.companyInfoService.save(customerLoan.getCompanyInfo()));
        }
        if (customerLoan.getFinancial() != null) {
            customerLoan.setFinancial(this.financialService.save(customerLoan.getFinancial()));
        }
        if (customerLoan.getSecurity() != null) {
            customerLoan.setSecurity(this.securityService.save(customerLoan.getSecurity()));
        }
        if (customerLoan.getSiteVisit() != null) {
            customerLoan.setSiteVisit(this.siteVisitService.save(customerLoan.getSiteVisit()));
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

        return customerLoanRepository.save(customerLoan);
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
        ProductMode productMode = findActiveProductMode();
        switch (productMode.getProduct()) {
            case DMS:
//                statistics = getDmsStatistics(branchId, startDate, endDate);
                break;
            case LAS:
                statistics = getLasStatistics(branchId, startDate, endDate);
                break;
            default:
        }
        return statistics;
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


    private ProductMode findActiveProductMode() {
        ProductMode productMode = productModeService.getByProduct(Product.DMS, Status.ACTIVE);
        if (productMode == null) {
            productMode = productModeService.getByProduct(Product.LAS, Status.ACTIVE);
        }
        return productMode;
    }

//    private List<StatisticDto> getDmsStatistics(Long branchId, String startDate, String endDate)
//        throws ParseException {
//        List<StatisticDto> data = new ArrayList<>();
//        if (branchId == 0) {
//            List<Long> branches = userService.getRoleAccessFilterByBranch();
//            if ((startDate == null || startDate.isEmpty()) && (endDate == null || endDate
//                .isEmpty())) {
//                data = customerLoanRepository.getDmsStatistics(branches);
//            } else if (startDate == null || startDate.isEmpty()) {
//                data = customerLoanRepository.getDmsStatisticsAndDateBefore(branches,
//                    new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
//            } else if (endDate == null || endDate.isEmpty()) {
//                data = customerLoanRepository
//                    .getDmsStatisticsAndDateAfter(branches, new SimpleDateFormat(
//                        "MM/dd/yyyy").parse(startDate));
//            } else {
//                data = customerLoanRepository
//                    .getDmsStatisticsAndDateBetween(branches, new SimpleDateFormat(
//                            "MM/dd/yyyy").parse(startDate),
//                        new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
//            }
//        } else {
//            if ((startDate == null || startDate.isEmpty()) && (endDate == null || endDate
//                .isEmpty())) {
//                data = customerLoanRepository.getDmsStatisticsByBranchId(branchId);
//            } else if (startDate == null || startDate.isEmpty()) {
//                data = customerLoanRepository.getDmsStatisticsByBranchIdAndDateBefore(branchId,
//                    new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
//            } else if (endDate == null || endDate.isEmpty()) {
//                data = customerLoanRepository.getDmsStatisticsByBranchIdAndDateAfter(branchId,
//                    new SimpleDateFormat(
//                        "MM/dd/yyyy").parse(startDate));
//            } else {
//                data = customerLoanRepository.getDmsStatisticsByBranchIdAndDateBetween(branchId,
//                    new SimpleDateFormat(
//                        "MM/dd/yyyy").parse(startDate),
//                    new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
//            }
//        }
//        return data;
//    }

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
        final CsvMaker csvMaker = new CsvMaker();
        final ObjectMapper objectMapper = new ObjectMapper();
        final User u = userService.getAuthenticatedUser();
        Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
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
            customerLoanCsvDto.setLoan(c.getLoan());
            customerLoanCsvDto.setProposal(c.getProposal());
            customerLoanCsvDto.setLoanType(c.getLoanType());
            customerLoanCsvDto.setLoanCategory(c.getLoanCategory());
            customerLoanCsvDto.setDocumentStatus(c.getDocumentStatus());
            customerLoanCsvDto.setToUser(c.getCurrentStage().getToUser());
            customerLoanCsvDto.setToRole(c.getCurrentStage().getToRole());
            customerLoanCsvDto.setCreatedAt(formatCsvDate(c.getCurrentStage().getCreatedAt()));
            customerLoanCsvDto.setCurrentStage(c.getCurrentStage());
            if (c.getDocumentStatus() == DocStatus.PENDING ||
                c.getDocumentStatus() == DocStatus.DOCUMENTATION ||
                c.getDocumentStatus() == DocStatus.VALUATION ||
                c.getDocumentStatus() == DocStatus.UNDER_REVIEW ||
                c.getDocumentStatus() == DocStatus.DISCUSSION) {
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
        Map<String, String> header = new LinkedHashMap<>();
        header.put("branch,name", " Branch");
        header.put("customerInfo,customerName", "Name");
        header.put("loan,name", "Loan Name");
        header.put("proposal,proposedLimit", "Proposed Amount");
        header.put("loanType", "Type");
        header.put("loanCategory", "Loan Category");
        header.put("documentStatus", "Status");
        header.put("toUser,name", "Current Position");
        header.put("toRole,roleName", "Designation");
        header.put("loanPossession", "Possession Under Days");
        for (CustomerLoan c : customerLoanList) {
            if (c.getDocumentStatus() == DocStatus.PENDING) {
                header.put("loanPendingSpan", "Loan Lifespan");
            } else {
                header.put("loanSpan", "Loan Lifespan");
            }
        }
        header.put("createdAt", "Created At");
        return csvMaker.csv("customer_loan", header, csvDto, UploadDir.customerLoanCsv);
    }

    @Override
    public List<CustomerLoan> getLoanByCustomerId(Long id) {
        return customerLoanRepository.getCustomerLoanByCustomerInfoId(id);
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
    public Page<Customer> getCustomerFromCustomerLoan(Object searchDto, Pageable pageable) {
        List<Customer> customerList = new ArrayList<>();
        final ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
        s.put("distinctByCustomer", "true");
        s.values().removeIf(Objects::isNull);
        logger.info("search param for customer in customerLoan {}", s);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        Page<CustomerLoan> customerLoanPage = customerLoanRepository
            .findAll(specification, pageable);
        customerLoanPage.getContent().forEach(customerLoan -> {
            if (!customerList.contains(customerLoan)) {
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

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}


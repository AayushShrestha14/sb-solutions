package com.sb.solutions.api.loan.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.approvallimit.emuns.LoanApprovalType;
import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.companyInfo.entityInfo.service.EntityInfoService;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.service.CustomerService;
import com.sb.solutions.api.dms.dmsloanfile.service.DmsLoanFileService;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.PieChartDto;
import com.sb.solutions.api.loan.StatisticDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.loanConfig.service.LoanConfigService;
import com.sb.solutions.api.productMode.entity.ProductMode;
import com.sb.solutions.api.productMode.service.ProductModeService;
import com.sb.solutions.api.siteVisit.entity.SiteVisit;
import com.sb.solutions.api.siteVisit.service.SiteVisitService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.Product;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.PathBuilder;
import com.sb.solutions.core.utils.csv.CsvMaker;
import com.sb.solutions.core.utils.jsonConverter.JsonConverter;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Service
public class CustomerLoanServiceImpl implements CustomerLoanService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoanService.class);
    private final CustomerLoanRepository customerLoanRepository;
    private final UserService userService;
    private final ProductModeService productModeService;
    private final CustomerService customerService;
    private final DmsLoanFileService dmsLoanFileService;
    private final EntityInfoService entityInfoService;
    private final LoanConfigService loanConfigService;
    private final SiteVisitService siteVisitService;
    private JsonConverter jsonConverter = new JsonConverter();

    public CustomerLoanServiceImpl(@Autowired CustomerLoanRepository customerLoanRepository,
        @Autowired UserService userService,
        @Autowired CustomerService customerService,
        @Autowired EntityInfoService entityInfoService,
        @Autowired LoanConfigService loanConfigService,
        @Autowired DmsLoanFileService dmsLoanFileService,
        @Autowired SiteVisitService siteVisitService,
        ProductModeService productModeService) {
        this.customerLoanRepository = customerLoanRepository;
        this.userService = userService;
        this.productModeService = productModeService;
        this.customerService = customerService;
        this.entityInfoService = entityInfoService;
        this.loanConfigService = loanConfigService;
        this.dmsLoanFileService = dmsLoanFileService;
        this.siteVisitService = siteVisitService;
    }

    @Override
    public List<CustomerLoan> findAll() {
        return customerLoanRepository.findAll();
    }

    @Override
    public CustomerLoan findOne(Long id) {
        CustomerLoan customerLoan = customerLoanRepository.findById(id).get();
        if (customerLoan.getFinancial() != null) {
            String url = customerLoan.getFinancial().getPath();
            customerLoan.getFinancial()
                .setData(this.jsonConverter.readJsonFile(url));
        }
        if (customerLoan.getSiteVisit() != null) {
            List<SiteVisit> siteVisitList = customerLoan.getSiteVisit();
            for (SiteVisit siteVisit : siteVisitList
            ) {
                String url = siteVisit.getPath();
                siteVisit.setData(this.jsonConverter.readJsonFile(url));
            }

        }
        return customerLoan;
    }

    @Override
    public CustomerLoan save(CustomerLoan customerLoan) {
        if (customerLoan.getLoan() == null) {
            throw new ServiceValidationException("Loan can not be null");
        }

        customerLoan.getDmsLoanFile()
            .setDocumentPath(new Gson().toJson(customerLoan.getDmsLoanFile().getDocumentMap()));
        customerLoan.getDmsLoanFile().setCreatedAt(new Date());

        Customer customer = null;
        EntityInfo entityInfo = null;
        if (customerLoan.getCustomerInfo() != null) {
            customer = this.customerService.save(customerLoan.getCustomerInfo());
        }
        if (customerLoan.getEntityInfo() != null
            && customerLoan.getLoanCategory() == LoanApprovalType.BUSINESS_TYPE) {
            entityInfo = this.entityInfoService.save(customerLoan.getEntityInfo());
        }
        if (customerLoan.getId() == null) {
            customerLoan.setBranch(userService.getAuthenticated().getBranch().get(0));
            LoanStage stage = new LoanStage();
            stage.setToRole(userService.getAuthenticated().getRole());
            stage.setFromRole(userService.getAuthenticated().getRole());
            stage.setFromUser(userService.getAuthenticated());
            stage.setToUser(userService.getAuthenticated());
            stage.setComment(DocAction.DRAFT.name());
            stage.setDocAction(DocAction.DRAFT);
            customerLoan.setCurrentStage(stage);

        }

        if (customerLoan.getDmsLoanFile() != null) {
            customerLoan.setDmsLoanFile(dmsLoanFileService.save(customerLoan.getDmsLoanFile()));
        }
        if (customerLoan.getFinancial() != null) {
            if (customerLoan.getFinancial().getId() == null) {
                try {
                    String url = UploadDir.initialDocument
                        + customerLoan.getBranch().getName()
                        + "/"
                        + customerLoan.getCustomerInfo().getCustomerName().replace(" ", "_")
                        + "_"
                        + customerLoan.getCustomerInfo().getCitizenshipNumber()
                        + "/"
                        + loanConfigService.findOne(customerLoan.getLoan().getId()).getName()
                        + "/"
                        + customerLoan.getLoanType()
                        + "/"
                        + customerLoan.getLoan().getId() + "/";
                    String jsonFileName;
                    String financial = "financial";
                    jsonFileName = url + financial + System.currentTimeMillis() + ".json";
                    customerLoan.getFinancial()
                        .setPath(this.jsonConverter.writeJsonFile(url, jsonFileName,
                            customerLoan.getFinancial().getData()));
                } catch (Exception exception) {
                    throw new ServiceValidationException("File Fail to Save");
                }
            } else {
                try {
                    String url = customerLoan.getFinancial().getPath();
                    customerLoan.getFinancial()
                        .setPath(this.jsonConverter
                            .updateJsonFile(url, customerLoan.getFinancial().getData()));
                } catch (Exception exception) {
                    throw new ServiceValidationException("File Fail to Save");
                }
            }
        }
        List<SiteVisit> siteVisitList = customerLoan.getSiteVisit();
        List<SiteVisit> siteVisitTemp = null;
        if (customerLoan.getSiteVisit() != null) {

            if (true) {
                siteVisitTemp = siteVisitList;
                try {
                    String uploadPath = new PathBuilder(UploadDir.initialDocument)
                        .withAction(
                            customerLoan.getLoanType().toString().toLowerCase().replace("\\s+", "")
                                .replace("loan", "").trim())
                        .isJsonPath(true)
                        .withBranch(customerLoan.getBranch().getName())
                        .withCitizenship(customerLoan.getCustomerInfo().getCitizenshipNumber())
                        .withCustomerName(customerLoan.getCustomerInfo().getCustomerName())
                        .withLoanType(customerLoan.getLoanType().toString()).build();

                    String jsonFileName;
                    String site = "siteVisit";
                    jsonFileName = uploadPath + site + System.currentTimeMillis() + ".json";
                    for (SiteVisit siteVisit : siteVisitList
                    ) {
                        siteVisit
                            .setPath(jsonConverter
                                .writeJsonFile(uploadPath, jsonFileName, siteVisit.getData()));
                    }
                    siteVisitTemp = this.siteVisitService.saveAll(siteVisitList);

                } catch (Exception e) {
                    throw new ServiceValidationException("Fail to Save File");
                }
            } else {
                try {
                    for (SiteVisit siteVisit : siteVisitList
                    ) {
                        String url = siteVisit.getPath();
                        siteVisit
                            .setPath(jsonConverter.updateJsonFile(url, siteVisit.getData()));
                    }

                } catch (Exception ex) {
                    throw new ServiceValidationException("Fail to Save File");
                }
            }
        }

        customerLoan.setSiteVisit(siteVisitTemp);
        customerLoan.setCustomerInfo(customer);
        customerLoan.setEntityInfo(entityInfo);
        return customerLoanRepository.save(customerLoan);
    }

    @Override
    public Page<CustomerLoan> findAllPageable(Object t, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(t, Map.class);
        User u = userService.getAuthenticated();
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
        User u = userService.getAuthenticated();
        List<Long> branchAccess = userService.getRoleAccessFilterByBranch();
        return customerLoanRepository.statusCount(u.getRole().getId(), branchAccess);
    }

    @Override
    public List<CustomerLoan> getFirst5CustomerLoanByDocumentStatus(DocStatus status) {
        User u = userService.getAuthenticated();
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
        User u = userService.getAuthenticated();
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
                statistics = getDmsStatistics(branchId, startDate, endDate);
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

    private List<StatisticDto> getDmsStatistics(Long branchId, String startDate, String endDate)
        throws ParseException {
        List<StatisticDto> data = new ArrayList<>();
        if (branchId == 0) {
            List<Long> branches = userService.getRoleAccessFilterByBranch();
            if ((startDate == null || startDate.isEmpty()) && (endDate == null || endDate
                .isEmpty())) {
                data = customerLoanRepository.getDmsStatistics(branches);
            } else if (startDate == null || startDate.isEmpty()) {
                data = customerLoanRepository.getDmsStatisticsAndDateBefore(branches,
                    new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
            } else if (endDate == null || endDate.isEmpty()) {
                data = customerLoanRepository
                    .getDmsStatisticsAndDateAfter(branches, new SimpleDateFormat(
                        "MM/dd/yyyy").parse(startDate));
            } else {
                data = customerLoanRepository
                    .getDmsStatisticsAndDateBetween(branches, new SimpleDateFormat(
                            "MM/dd/yyyy").parse(startDate),
                        new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
            }
        } else {
            if ((startDate == null || startDate.isEmpty()) && (endDate == null || endDate
                .isEmpty())) {
                data = customerLoanRepository.getDmsStatisticsByBranchId(branchId);
            } else if (startDate == null || startDate.isEmpty()) {
                data = customerLoanRepository.getDmsStatisticsByBranchIdAndDateBefore(branchId,
                    new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
            } else if (endDate == null || endDate.isEmpty()) {
                data = customerLoanRepository.getDmsStatisticsByBranchIdAndDateAfter(branchId,
                    new SimpleDateFormat(
                        "MM/dd/yyyy").parse(startDate));
            } else {
                data = customerLoanRepository.getDmsStatisticsByBranchIdAndDateBetween(branchId,
                    new SimpleDateFormat(
                        "MM/dd/yyyy").parse(startDate),
                    new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
            }
        }
        return data;
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
    public CustomerLoan renewCloseEntity(CustomerLoan object) {
        List<SiteVisit> siteVisitList = object.getSiteVisit();
        final Long tempParentId = object.getId();
        object.setParentId(tempParentId);
        object.setId(null);
        object.setDocumentStatus(DocStatus.PENDING);

        if (object.getDmsLoanFile() != null) {
            object.getDmsLoanFile().setId(null);
            object.setDmsLoanFile(dmsLoanFileService.save(object.getDmsLoanFile()));
        }
        object.getDmsLoanFile().setId(null);
        if (object.getFinancial() != null) {
            object.getFinancial().setId(null);
        }
        if (object.getSiteVisit() != null) {
            for (SiteVisit siteVisit : siteVisitList
            ) {
                siteVisit.setId(null);
            }
        }

        LoanStage stage = new LoanStage();
        stage.setToRole(userService.getAuthenticated().getRole());
        stage.setFromRole(userService.getAuthenticated().getRole());
        stage.setFromUser(userService.getAuthenticated());
        stage.setToUser(userService.getAuthenticated());
        stage.setComment(DocAction.DRAFT.name());
        stage.setDocAction(DocAction.DRAFT);
        object.setCurrentStage(stage);
        CustomerLoan customerLoan = customerLoanRepository.save(object);
        customerLoanRepository.updateCloseRenewChildId(customerLoan.getId(), tempParentId);
        return customerLoan;
    }

    @Override
    public String csv(Object searchDto) {
        final CsvMaker csvMaker = new CsvMaker();
        final ObjectMapper objectMapper = new ObjectMapper();
        User u = userService.getAuthenticated();
        Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
        String branchAccess = userService.getRoleAccessFilterByBranch().stream()
            .map(Object::toString).collect(Collectors.joining(","));
        if (s.containsKey("branchIds")) {
            branchAccess = s.get("branchIds");
        }
        s.put("branchIds", branchAccess);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        final List customerLoanList = customerLoanRepository.findAll(specification);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("branch,name", " Branch");
        header.put("customerInfo,customerName", "Name");
        header.put("loan,name", "Loan Name");
        header.put("dmsLoanFile,proposedAmount", "Proposed Amount");
        header.put("loanType", "Type");
        header.put("loanCategory", "Loan Category");
        header.put("documentStatus", "Status");
        return csvMaker.csv("customer_loan", header, customerLoanList, UploadDir.customerLoanCsv);
    }

}


package com.sb.solutions.api.loan.service;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.companyInfo.entityInfo.service.EntityInfoService;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.service.CustomerService;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.PieChartDto;
import com.sb.solutions.api.loan.StatisticDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.productMode.entity.ProductMode;
import com.sb.solutions.api.productMode.service.ProductModeService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.enums.*;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.csv.CsvMaker;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    private final EntityInfoService entityInfoService;

    public CustomerLoanServiceImpl(@Autowired CustomerLoanRepository customerLoanRepository,
        @Autowired UserService userService,
        @Autowired CustomerService customerService,
        @Autowired EntityInfoService entityInfoService,
        ProductModeService productModeService) {
        this.customerLoanRepository = customerLoanRepository;
        this.userService = userService;
        this.productModeService = productModeService;
        this.customerService = customerService;
        this.entityInfoService = entityInfoService;
    }

    @Override
    public List<CustomerLoan> findAll() {
        return customerLoanRepository.findAll();
    }

    @Override
    public CustomerLoan findOne(Long id) {
        return customerLoanRepository.findById(id).get();
    }

    @Override
    public CustomerLoan save(CustomerLoan customerLoan) {
        if (customerLoan.getLoan() == null) {
            throw new ServiceValidationException("Loan can not be null");
        }

        Customer customer = null;
        EntityInfo entityInfo = null;
        if (customerLoan.getCustomerInfo() != null) {
            customer = this.customerService.save(customerLoan.getCustomerInfo());
        }
        if (customerLoan.getEntityInfo() != null) {
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
        s.put("branchIds", branchAccess == null ? null : branchAccess);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        return customerLoanRepository.findAll(specification, pageable);
    }

    @Override
    public void sendForwardBackwardLoan(CustomerLoan customerLoan) {
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
    public List<PieChartDto> proposedAmount() {
        List<Long> branchAccess = userService.getRoleAccessFilterByBranch();
        return customerLoanRepository.proposedAmount(branchAccess);
    }

    @Override
    public List<PieChartDto> proposedAmountByBranch(Long branchId) {
        return customerLoanRepository.proposedAmountByBranchId(branchId);
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
        s.put("branchIds", branchAccess == null ? null : branchAccess);
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
    public List<StatisticDto> getStats(Long branchId) {
        List<StatisticDto> statistics = new ArrayList<>();
        logger.debug("Request to get the statistics about the existing loans.");
        ProductMode productMode = findActiveProductMode();
        switch (productMode.getProduct()) {
            case DMS:
                statistics = getDmsStatistics(branchId);
                break;
            case LAS:
                statistics = getLasStatistics(branchId);
                break;
            default:
        }
        return statistics;
    }

    @Override
    public Map<String, String> chkUserContainCustomerLoan(Long id) {
        Integer count = customerLoanRepository.chkUserContainCustomerLoan(id, DocStatus.PENDING);
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

    private List<StatisticDto> getDmsStatistics(Long branchId) {
        if (branchId == 0) {
            List<Long> branches = userService.getRoleAccessFilterByBranch();
            return customerLoanRepository.getDmsStatistics(branches);
        } else {
            return customerLoanRepository.getDmsStatisticsByBranchId(branchId);
        }
    }

    private List<StatisticDto> getLasStatistics(Long branchId) {
        if (branchId == 0) {
            List<Long> branches = userService.getRoleAccessFilterByBranch();
            return customerLoanRepository.getLasStatistics(branches);
        } else {
            return customerLoanRepository.getLasStatisticsByBranchId(branchId);
        }
    }

    @Override
    public CustomerLoan renewCloseEntity(CustomerLoan object) {
        final Long tempParentId = object.getId();
        object.setParentId(tempParentId);
        object.setId(null);
        object.setDocumentStatus(DocStatus.PENDING);
        object.getDmsLoanFile().setId(null);

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
        Map<String, String> s = objectMapper.convertValue(searchDto, Map.class);
        String branchAccess = userService.getRoleAccessFilterByBranch().stream()
            .map(Object::toString).collect(Collectors.joining(","));
        if (s.containsKey("branchIds")) {
            branchAccess = s.get("branchIds");
        }
        s.put("branchIds", branchAccess == null ? null : branchAccess);
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        final List customerLoanList = customerLoanRepository.findAll(specification);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("branch,name", " Branch");
        header.put("dmsLoanFile,customerName", "Name");
        header.put("loan,name", "Loan Name");
        header.put("dmsLoanFile,proposedAmount", "Proposed Amount");
        header.put("loanType", "Type");
        header.put("loanCategory", "Loan Category");
        header.put("documentStatus", "Status");
        return csvMaker.csv("customer_loan", header, customerLoanList, UploadDir.customerLoanCsv);
    }


}


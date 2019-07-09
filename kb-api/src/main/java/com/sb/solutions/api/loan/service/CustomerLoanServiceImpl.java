package com.sb.solutions.api.loan.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.FilePath;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.exception.ServiceValidationException;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Service
public class CustomerLoanServiceImpl implements CustomerLoanService {

    private final CustomerLoanRepository customerLoanRepository;
    private final UserService userService;
    private com.sb.solutions.core.utils.jsonConverter.JsonConverter jsonConverter = new com.sb.solutions.core.utils.jsonConverter.JsonConverter();

    public CustomerLoanServiceImpl(@Autowired CustomerLoanRepository customerLoanRepository,
        @Autowired UserService userService) {
        this.customerLoanRepository = customerLoanRepository;
        this.userService = userService;
    }

    @Override
    public List<CustomerLoan> findAll() {
        return customerLoanRepository.findAll();
    }

    @Override
    public CustomerLoan findOne(Long id) {
        CustomerLoan customerLoan = customerLoanRepository.findById(id).get();
//        String url = customerLoan.getFinancial().getPath();
//        customerLoan.getFinancial()
//            .setPath(readJsonFile(url));
        return customerLoan;
    }

    @Override
    public CustomerLoan save(CustomerLoan customerLoan) {
        if (customerLoan.getLoan() == null) {
            throw new ServiceValidationException("Loan can not be null");
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
//        if (customerLoan.getFinancial().getId() == null) {
//            try {
//                String url = FilePath.getOSPath() + UploadDir.initialDocument + customerLoan
//                    .getCustomerInfo().getCustomerName()
//                    + "_"
//                    + customerLoan.getCustomerInfo().getCitizenshipNumber()
//                    + "/"
//                    + customerLoan.getLoan().getName() + "/";
//                customerLoan.getFinancial()
//                    .setPath(writeJsonFile(url, customerLoan.getFinancial().getData()));
//            } catch (Exception exception) {
//                throw new ServiceValidationException("File Fail to Save");
//            }
//        } else {
//            try {
//                String url = customerLoan.getFinancial().getPath();
//                customerLoan.getFinancial()
//                    .setPath(updateJsonFile(url, customerLoan.getFinancial().getData()));
//            } catch (Exception exception) {
//                throw new ServiceValidationException("File Fail to Save");
//            }
//        }
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
    public List<Map<String, Double>> proposedAmount() {
        List<Long> branchAccess = userService.getRoleAccessFilterByBranch();
        return customerLoanRepository.proposedAmount(branchAccess);
    }

    @Override
    public List<Map<String, Double>> proposedAmountByBranch(Long branchId) {
        return customerLoanRepository.proposedAmountByBranchId(branchId);
    }

    @Override
    public List<CustomerLoan> getByCitizenshipNumber(String citizenshipNumber) {
        return customerLoanRepository
            .getByCustomerInfoCitizenshipNumberOrDmsLoanFileCitizenshipNumber(citizenshipNumber,
                citizenshipNumber);
    }

    @Override
    public Page<CustomerLoan> getCatalogues(Object searchDto, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
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

    public String writeJsonFile(String url, String financialData) {
        String jsonPath;
        String FINANCIAL = "financial";
        Path path = Paths.get(FilePath.getOSPath() + url);
        if (!Files.exists(path)) {
            new File(FilePath.getOSPath() + url).mkdirs();
        }
        jsonPath = url + FINANCIAL + System.currentTimeMillis() + ".json";
        File file = new File(FilePath.getOSPath() + jsonPath);
        file.getParentFile().mkdirs();
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.write(jsonConverter.convertToJson(financialData));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.flush();
            return jsonPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String updateJsonFile(String url, String financialData) {
        String jsonPath = url;
        try {
            FileWriter writer = new FileWriter(FilePath.getOSPath() + url);
            try {
                writer.write(jsonConverter.convertToJson(financialData));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.flush();
                return jsonPath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readJsonFile(String url) {
        return null;
    }
}


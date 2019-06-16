package com.sb.solutions.api.Loan.service;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.Loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.Loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.repository.BranchRepository;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.loanConfig.repository.LoanConfigRepository;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.exception.ApiException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Service
public class CustomerLoanServiceImpl implements CustomerLoanService {

    @Autowired
    CustomerLoanRepository customerLoanRepository;

    @Autowired
    UserService userService;

    @Autowired
    LoanConfigRepository loanConfigRepository;

    @Autowired
    BranchRepository branchRepository;

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
            throw new ApiException("Loan Cannot be null");
        }
        return customerLoanRepository.save(customerLoan);
    }

    @Override
    public Page<CustomerLoan> findAllPageable(Object t, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(t, Map.class);
        s.put("currentUserRole", userService.getAuthenticated().getRole().getId().toString());
        s.put("createdBy", userService.getAuthenticated().getId().toString());
        final CustomerLoanSpecBuilder customerLoanSpecBuilder = new CustomerLoanSpecBuilder(s);
        final Specification<CustomerLoan> specification = customerLoanSpecBuilder.build();
        return customerLoanRepository.findAll(specification, pageable);
    }

    @Override
    public void sendForwardBackwardLoan(CustomerLoan customerLoan) {
        customerLoanRepository.save(customerLoan);
    }

    @Override
    public Map<Object, Object> statusCount() {
        return customerLoanRepository.statusCount();
    }

    @Override
    public List<CustomerLoan> getFirst5CustomerLoanByDocumentStatus(DocStatus status) {
        return customerLoanRepository
                .findFirst5ByDocumentStatusAndCurrentStageToRoleIdOrderByIdDesc(status,
                        userService.getAuthenticated().getRole().getId());
    }

    @Override
    public List<Map<Object, Object>> proposedAmount() {
        return customerLoanRepository.proposedAmount();
    }

    @Override
    public List<Map<Object, Object>> proposedAmountByBranch(Long branchId) {
        return customerLoanRepository.proposedAmountByBranchId(branchId);
    }

}


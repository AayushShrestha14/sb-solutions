package com.sb.solutions.web.loan.v1.mapper;

import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.Loan.service.CustomerLoanService;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.web.loan.v1.dto.LoanActionDto;
import com.sb.solutions.web.loan.v1.dto.LoanDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Component
@AllArgsConstructor
public class Mapper {

    private final CustomerLoanService customerLoanService;
    private final UserService userService;

    public CustomerLoan ActionMapper(LoanActionDto loanActionDto) {
        CustomerLoan customerLoan = customerLoanService.findOne(loanActionDto.getId());

        User receivedBy = new User();
        receivedBy.setId(loanActionDto.getToUser());
        LoanConfig loan = new LoanConfig();
        loan.setId(loanActionDto.getLoanType());

        LoanStage loanStage = new LoanStage();
        loanStage.setDocAction(loanActionDto.getDocAction());
        loanStage.setToUser(receivedBy);
        loanStage.setFromUser(userService.getAuthenticated());
        customerLoan.setCurrentStage(loanStage);
        customerLoan.setLoan(loan);
        return customerLoan;

    }

    public CustomerLoan loanMapper(LoanDto loanDto) {
        CustomerLoan customerLoan = new CustomerLoan();
        customerLoan = customerLoanService.findOne(loanDto.getId());

        return null;
    }
}

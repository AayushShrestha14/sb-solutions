package com.sb.solutions.web.loan.v1.mapper;

import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.Loan.service.CustomerLoanService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.web.loan.v1.dto.LoanActionDto;
import com.sb.solutions.web.loan.v1.dto.LoanDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Component
@AllArgsConstructor
public class Mapper {

    private final CustomerLoanService customerLoanService;
    private final UserService userService;


    public CustomerLoan ActionMapper(LoanActionDto loanActionDto) {

        CustomerLoan customerLoan = customerLoanService.findOne(loanActionDto.getCustomerId());
        User currentUser = userService.getAuthenticated();
        User receivedBy = userService.findByRoleAndBranch(loanActionDto.getToUser(), currentUser.getBranch());
        customerLoan.setLoanType(LoanType.NEW_LOAN);
        LoanStage loanStage = new LoanStage();
        List<LoanStage> previousList = customerLoan.getPreviousStageList();
        if ( customerLoan.getCurrentStage().getId() != null) {
            previousList.add(loanStage);
        }


        loanStage.setDocAction(loanActionDto.getDocAction());

        loanStage.setFromUser(currentUser);
        loanStage.setToUser(receivedBy);
        customerLoan.setCurrentStage(loanStage);
        customerLoan.setPreviousStageList(previousList);
        return customerLoan;

    }

    public CustomerLoan loanMapper(LoanDto loanDto) {
        CustomerLoan customerLoan = new CustomerLoan();
        customerLoan = customerLoanService.findOne(loanDto.getId());

        return null;
    }
}

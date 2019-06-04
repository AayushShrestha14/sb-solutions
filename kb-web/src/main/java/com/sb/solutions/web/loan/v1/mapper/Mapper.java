package com.sb.solutions.web.loan.v1.mapper;

import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.Loan.entity.Loan;
import com.sb.solutions.api.Loan.service.LoanService;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.web.loan.v1.dto.LoanActionDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Component
@AllArgsConstructor
public class Mapper {

    private final LoanService loanService;
    private final UserService userService;

    public Loan ActionMapper(LoanActionDto loanActionDto) {
        Loan loan = loanService.findOne(loanActionDto.getId());

        User receivedBy = new User();
        receivedBy.setId(loanActionDto.getToUser());
        LoanConfig loanType = new LoanConfig();
        loanType.setId(loanActionDto.getLoanType());

        LoanStage loanStage = new LoanStage();
        loanStage.setDocAction(loanActionDto.getDocAction());
        loanStage.setToUser(receivedBy);
        loanStage.setFromUser(userService.getAuthenticated());
        loan.setStage(loanStage);
        loan.setLoanType(loanType);
        return loan;

    }
}

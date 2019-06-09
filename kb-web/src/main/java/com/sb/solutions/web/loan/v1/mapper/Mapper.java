package com.sb.solutions.web.loan.v1.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.Loan.service.CustomerLoanService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.web.loan.v1.dto.LoanActionDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        List previousList = customerLoan.getPreviousList();
        List previousListTemp = new ArrayList();
        LoanStage loanStage = new LoanStage();
        if (customerLoan.getCurrentStage() != null) {
            loanStage = customerLoan.getCurrentStage();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            Map<String, String> tempLoanStage = objectMapper.convertValue(customerLoan.getCurrentStage(), Map.class);
            try {
                previousList.forEach(p -> {
                    try {
                        Map<String, String> previous = objectMapper.convertValue(p, Map.class);

                        previousListTemp.add(objectMapper.writeValueAsString(previous));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });

                String jsonValue = objectMapper.writeValueAsString(tempLoanStage);
                previousListTemp.add(jsonValue);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        customerLoan.setPreviousStageList(previousListTemp.toString());

        loanStage.setDocAction(loanActionDto.getDocAction());

        loanStage.setFromUser(currentUser);
        loanStage.setToUser(receivedBy);
        loanStage.setComment(loanActionDto.getComment() );
        customerLoan.setCurrentStage(loanStage);
        customerLoan.setPreviousList(previousListTemp);
        return customerLoan;

    }

    public CustomerLoan loanMapper(LoanActionDto loanDto) {
        CustomerLoan customerLoan = new CustomerLoan();
        customerLoan = customerLoanService.findOne(loanDto.getId());

        return null;
    }
}

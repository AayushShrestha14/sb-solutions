package com.sb.solutions.web.loan.v1.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.web.loan.v1.dto.LoanActionDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Component
public class Mapper {

    public CustomerLoan ActionMapper(LoanActionDto loanActionDto, CustomerLoan customerLoan, User currentUser) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        customerLoan.setLoanType(LoanType.NEW_LOAN);
        List previousList = customerLoan.getPreviousList();
        List previousListTemp = new ArrayList();
        LoanStage loanStage = new LoanStage();
        if (customerLoan.getCurrentStage() != null) {
            loanStage = customerLoan.getCurrentStage();

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
        User toUser = new User();
        Role toRole = new Role();
        toUser.setId(loanActionDto.getToUser());
        loanStage.setFromUser(currentUser);
        loanStage.setFromRole(currentUser.getRole());

        if (loanActionDto.getDocAction().equals(DocAction.BACKWARD)) {


            previousList.forEach(p -> {
                LoanStage maker = (LoanStage) previousList.get(0);
                if (maker.getFromUser().getId().equals(customerLoan.getCreatedBy())) {
                    toRole.setId(maker.getFromUser().getRole().getId());
                    toUser.setId(maker.getFromUser().getId());
                }
            });


        } else {

            toRole.setId(loanActionDto.getToRole());

        }
        loanStage.setToUser(toUser);
        loanStage.setToRole(toRole);
        loanStage.setComment(loanActionDto.getComment());
        customerLoan.setCurrentStage(loanStage);
        customerLoan.setPreviousList(previousListTemp);
        customerLoan.setBranch(currentUser.getBranch());


        return customerLoan;

    }

    public CustomerLoan loanMapper(LoanActionDto loanDto) {
        CustomerLoan customerLoan = new CustomerLoan();

        return null;
    }
}

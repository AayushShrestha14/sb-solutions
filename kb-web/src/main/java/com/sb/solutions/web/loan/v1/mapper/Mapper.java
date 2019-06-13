package com.sb.solutions.web.loan.v1.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.web.common.stage.dto.StageDto;
import com.sb.solutions.web.common.stage.mapper.StageMapper;
import com.sb.solutions.web.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Component
public class Mapper {

    @Autowired
    StageMapper stageMapper;

    public CustomerLoan ActionMapper(StageDto loanActionDto, CustomerLoan customerLoan, User currentUser) throws InvocationTargetException, IllegalAccessException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
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


        StageDto currentStage = objectMapper.convertValue(loanStage, StageDto.class);

        try {
            UserDto currentUserDto = objectMapper.convertValue(currentUser, UserDto.class);
            loanStage = this.loanStages(loanActionDto, previousList, customerLoan.getCreatedBy(), currentStage, currentUserDto);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        customerLoan.setCurrentStage(loanStage);
        customerLoan.setPreviousList(previousListTemp);
        return customerLoan;

    }


    public LoanStage loanStages(StageDto stageDto, List previousList, Long createdBy, StageDto currentStage, UserDto currentUser) throws InstantiationException, IllegalAccessException, InvocationTargetException {

        LoanStage s = stageMapper.mapper(stageDto, previousList, LoanStage.class, createdBy, currentStage, currentUser);
        return s;
    }
}

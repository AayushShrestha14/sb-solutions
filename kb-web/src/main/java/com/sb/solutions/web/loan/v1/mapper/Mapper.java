package com.sb.solutions.web.loan.v1.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.approvallimit.entity.ApprovalLimit;
import com.sb.solutions.api.approvallimit.service.ApprovalLimitService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.web.common.stage.dto.StageDto;
import com.sb.solutions.web.common.stage.mapper.StageMapper;
import com.sb.solutions.web.user.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Component
public class Mapper {

    private final StageMapper stageMapper;

    private final ApprovalLimitService approvalLimitService;
    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);


    public Mapper(@Autowired StageMapper stageMapper,
                  @Autowired ApprovalLimitService approvalLimitService) {
        this.stageMapper = stageMapper;
        this.approvalLimitService = approvalLimitService;
    }

    public CustomerLoan ActionMapper(StageDto loanActionDto, CustomerLoan customerLoan, User currentUser) {
        if (loanActionDto.getDocAction().equals(DocAction.APPROVED)) {
            ApprovalLimit approvalLimit = approvalLimitService.getByRoleAndLoan(customerLoan.getLoan().getId(), currentUser.getRole().getId());
            if (approvalLimit == null) {
                throw new RuntimeException("Authority Limit Error");
            }
            if (customerLoan.getDmsLoanFile() != null) {
                if (customerLoan.getDmsLoanFile().getProposedAmount() > approvalLimit.getAmount()) {
                    throw new RuntimeException("Authority Limit Error");
                }
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
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
        customerLoan.setDocumentStatus(loanActionDto.getDocumentStatus());
        StageDto currentStage = objectMapper.convertValue(loanStage, StageDto.class);
        UserDto currentUserDto = objectMapper.convertValue(currentUser, UserDto.class);
        loanStage = this.loanStages(loanActionDto, previousList, customerLoan.getCreatedBy(), currentStage, currentUserDto);
        customerLoan.setCurrentStage(loanStage);
        customerLoan.setPreviousList(previousListTemp);
        return customerLoan;
    }

    private LoanStage loanStages(StageDto stageDto, List previousList, Long createdBy, StageDto currentStage, UserDto currentUser) {
        if (currentStage.getDocAction().equals(DocAction.CLOSED) ||
                currentStage.getDocAction().equals(DocAction.APPROVED) ||
                currentStage.getDocAction().equals(DocAction.REJECT)) {
            logger.error("Error while performing the action");
            throw new RuntimeException("Cannot Perform the action");
        }
        if (stageDto.getDocAction().equals(DocAction.FORWARD)) {
            if (stageDto.getToRole() == null || stageDto.getToUser() == null) {
                logger.error("Error while performing the action");
                throw new RuntimeException("Cannot Perform the action");
            }
        }
        return stageMapper.mapper(stageDto, previousList, LoanStage.class, createdBy, currentStage, currentUser);
    }
}

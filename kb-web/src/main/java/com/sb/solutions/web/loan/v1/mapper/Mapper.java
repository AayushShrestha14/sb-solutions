package com.sb.solutions.web.loan.v1.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sb.solutions.api.approvallimit.entity.ApprovalLimit;
import com.sb.solutions.api.approvallimit.service.ApprovalLimitService;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.StatisticDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loanflag.entity.CustomerLoanFlag;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.ProductUtils;
import com.sb.solutions.web.common.stage.dto.StageDto;
import com.sb.solutions.web.common.stage.mapper.StageMapper;
import com.sb.solutions.web.customerInfo.v1.dto.CustomerTransferDTO;
import com.sb.solutions.web.loan.v1.dto.BarChartDto;
import com.sb.solutions.web.loan.v1.dto.SeriesDto;
import com.sb.solutions.web.user.dto.UserDto;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Component
public class Mapper {

    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);
    private final StageMapper stageMapper;
    private final ApprovalLimitService approvalLimitService;
    private final UserService userService;
    Gson gson = new Gson();

    public Mapper(@Autowired StageMapper stageMapper,
        @Autowired ApprovalLimitService approvalLimitService,
        UserService userService) {
        this.stageMapper = stageMapper;
        this.approvalLimitService = approvalLimitService;
        this.userService = userService;
    }

    public CustomerLoan actionMapper(StageDto loanActionDto, CustomerLoan customerLoan,
        User currentUser) {
        if ((loanActionDto.getDocAction() != DocAction.PULLED) && (loanActionDto
            .getDocAction() != DocAction.TRANSFER) && (loanActionDto.getDocAction()
            != DocAction.RE_INITIATE)) {
            if( !customerLoan.getCurrentStage().getToUser().getId().equals(currentUser.getId())){
                throw new ServiceValidationException("Sorry this document is not under you!!");
            }
        }
        if (loanActionDto.getDocAction() == DocAction.RE_INITIATE
            && customerLoan.getDocumentStatus() != DocStatus.REJECTED) {
            throw new ServiceValidationException(
                "Re-initiate failed: Document status should be REJECTED!");
        }
        if (loanActionDto.getDocAction() == DocAction.APPROVED) {
               Map<String , Object> proposalData = gson.fromJson(customerLoan.getProposal().getData() , HashMap.class);
               proposalData.replace("existingLimit" , customerLoan.getProposal().getProposedLimit());
               customerLoan.getProposal().setData(gson.toJson(proposalData));
               customerLoan.getProposal().setExistingLimit(customerLoan.getProposal().getProposedLimit());
            if (ProductUtils.LAS) {
                ApprovalLimit approvalLimit = approvalLimitService
                    .getByRoleAndLoan(currentUser.getRole().getId(),
                        customerLoan.getLoan().getId(), customerLoan.getLoanCategory());
                if (approvalLimit != null) {
                    if (Double.valueOf(approvalLimit.getAmount().toString()) < Double
                        .valueOf(customerLoan.getProposal().getProposedLimit().toString())) {
                        throw new RuntimeException("Authority Limit Exceed");
                    }
                } else {
                    throw new RuntimeException("Authority Limit Is not set yet for this loan");
                }
                if (customerLoan.getDmsLoanFile() != null) {
                    if (customerLoan.getDmsLoanFile().getProposedAmount().compareTo(approvalLimit
                        .getAmount()) == 1) {
                        // proposed amount is greater than approval limit
                        throw new RuntimeException("Amount Exceed");
                    }
                }
            }
            if (loanActionDto.isNotify()) {
                customerLoan.setNotify(true);
            }
            if (customerLoan.getIsSol()) {
                Preconditions.checkArgument(
                    customerLoan.getSolUser().getId() == currentUser.getId(),
                    "You don't have permission to Approve this file!!");
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        List previousList = customerLoan.getPreviousList();
        List previousListTemp = new ArrayList();
        LoanStage loanStage = new LoanStage();
        if (customerLoan.getCurrentStage() != null) {
            loanStage = customerLoan.getCurrentStage();
            Map<String, String> tempLoanStage = objectMapper
                .convertValue(customerLoan.getCurrentStage(), Map.class);
            try {
                previousList.forEach(p -> {
                    try {
                        Map<String, String> previous = objectMapper.convertValue(p, Map.class);

                        previousListTemp.add(objectMapper.writeValueAsString(previous));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to handle JSON data");
                    }
                });
                String jsonValue = objectMapper.writeValueAsString(tempLoanStage);
                previousListTemp.add(jsonValue);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to Get Stage data");
            }
        }
        if (loanActionDto.getDocAction() == DocAction.FORWARD
            || loanActionDto.getDocAction() == DocAction.BACKWARD) {
            if (customerLoan.getDocumentStatus() == DocStatus.UNDER_REVIEW || customerLoan
                .getDocumentStatus() == DocStatus.PENDING) {
                customerLoan.setDocumentStatus(DocStatus.PENDING);
            } else {
                throw new ServiceValidationException(
                    "Forward Failed:Document Status should be under Review or Pending!!");
            }
        }
        customerLoan.setPreviousStageList(previousListTemp.toString());
        customerLoan.setDocumentStatus(loanActionDto.getDocumentStatus());
        StageDto currentStage = objectMapper.convertValue(loanStage, StageDto.class);
        UserDto currentUserDto = objectMapper.convertValue(currentUser, UserDto.class);
        loanStage = this
            .loanStages(loanActionDto, previousList, customerLoan.getCreatedBy(), currentStage,
                currentUserDto, customerLoan);
        customerLoan.setCurrentStage(loanStage);
        customerLoan.setPreviousList(previousListTemp);
        if ((loanActionDto.getDocAction() == DocAction.FORWARD) && currentUser.getRole()
            .getRoleType() == RoleType.MAKER) {
            if (loanActionDto.getIsSol()) {
                User user = new User();
                Preconditions.checkNotNull(loanActionDto.getSolUser(),
                    "Please Select Approval User for Loan " + customerLoan.getLoan().getName());
                user.setId(loanActionDto.getSolUser().getId());
                customerLoan.setIsSol(Boolean.TRUE);
                customerLoan.setSolUser(user);
            } else {
                customerLoan.setIsSol(Boolean.FALSE);
                customerLoan.setSolUser(null);
            }
        }
        return customerLoan;
    }

    private LoanStage loanStages(StageDto stageDto, List previousList, Long createdBy,
        StageDto currentStage, UserDto currentUser, CustomerLoan customerLoan) {
        if (stageDto.getDocAction() == DocAction.NOTED) {
            customerLoan.setNotedBy(currentUser.getId());
        } else if (currentStage.getDocAction() == DocAction.CLOSED
            || currentStage.getDocAction() == DocAction.APPROVED) {
            logger.error("Error while performing the action");
            throw new ServiceValidationException(
                "Cannot Perform the action. Document has been " + currentStage.getDocAction());
        }
        // TODO: Separate alert message for no user and disabled user
        if (stageDto.getDocAction() == DocAction.FORWARD) {
            if (stageDto.getToRole() == null || stageDto.getToUser() == null) {
                logger.error("Error while performing the action");
                throw new ServiceValidationException(
                    "There is no user created in the role or is  disabled. Please contact admin.");
            }
            // Check loan flags
            List<CustomerLoanFlag> loanFlags = customerLoan.getLoanHolder().getLoanFlags()
                .stream()
                .filter(f -> f.getCustomerLoanId() != null && f.getCustomerLoanId()
                    .equals(customerLoan.getId()))
                .collect(Collectors.toList());
            if (!loanFlags.isEmpty()) {
                loanFlags.sort(Comparator.comparingInt(CustomerLoanFlag::getOrder));
                logger.error(loanFlags.get(0).getDescription());
                throw new RuntimeException(loanFlags.get(0).getDescription());
            }
        }
        return stageMapper
            .mapper(stageDto, previousList, LoanStage.class, createdBy, currentStage, currentUser,
                customerLoan);
    }

    public List<BarChartDto> toBarchartDto(List<StatisticDto> statistics) {
        final List<BarChartDto> charts = new ArrayList<>();
        Map<String, List<StatisticDto>> mappedStats =
            statistics.stream().collect(Collectors.groupingBy(StatisticDto::getLoanType));
        for (Map.Entry<String, List<StatisticDto>> entry : mappedStats.entrySet()) {
            final BarChartDto barChart = new BarChartDto();
            barChart.setName(entry.getKey());
            entry.getValue().forEach(statisticDto -> {
                final SeriesDto series = new SeriesDto();
                series.setName(statisticDto.getStatus().toString());
                series.setValue(statisticDto.getTotalAmount());
                series.setFileCount(statisticDto.getFiles());
                barChart.getSeries().add(series);
            });
            charts.add(barChart);
        }
        return charts;
    }

    public CustomerLoan transferBranchMapper(CustomerTransferDTO transferDTO, CustomerLoan customerLoan,
        User currentUser) {
        if (currentUser.getRole().getRoleType().equals(RoleType.ADMIN) || currentUser.getUsername().equalsIgnoreCase("SPADMIN")) {
            if (transferDTO.getDocAction().equals(DocAction.TRANSFER)) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                List previousList = customerLoan.getPreviousList();
                List previousListTemp = new ArrayList();
                LoanStage loanStage = new LoanStage();
                if (customerLoan.getCurrentStage() != null) {
                    loanStage = customerLoan.getCurrentStage();
                    Map<String, String> tempLoanStage = objectMapper
                        .convertValue(loanStage, Map.class);
                    try {
                        previousList.forEach(p -> {
                            try {
                                Map<String, String> previous = objectMapper
                                    .convertValue(p, Map.class);
                                previousListTemp.add(objectMapper.writeValueAsString(previous));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException("Failed to handle JSON data");
                            }
                        });
                        String currentLoanJsonValue = objectMapper.writeValueAsString(tempLoanStage);
                        previousListTemp.add(currentLoanJsonValue);
                        loanStage.setFromUser(loanStage.getToUser());
                        loanStage.setFromRole(loanStage.getToRole());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to get stage data");
                    }
                }
                customerLoan.setPreviousStageList(previousListTemp.toString());
                customerLoan.setPreviousList(previousListTemp);
                User toUser = userService.findOne(transferDTO.getToUserId());
                if(customerLoan.getCurrentStage() == null){
                    loanStage.setFromUser(toUser);
                    loanStage.setFromRole(toUser.getRole());
                }
                loanStage.setToUser(toUser);
                loanStage.setToRole(toUser.getRole());
                loanStage.setDocAction(transferDTO.getDocAction());
                loanStage.setComment("Transferred by " + currentUser.getRole().getRoleName() + ". " + transferDTO.getComment());
                objectMapper.convertValue(loanStage, LoanStage.class);
                customerLoan.setCurrentStage(loanStage);
                customerLoan.setCreatedBy(toUser.getId());  //can not be set
                return customerLoan;
            } else {
                throw new ServiceValidationException(
                    "Transfer Failed: Action other than branch transfer detected!!!");
            }
        } else {
            throw new ServiceValidationException(
                "Transfer Failed: You are not authorized to transfer branch!!!");
        }
    }
}

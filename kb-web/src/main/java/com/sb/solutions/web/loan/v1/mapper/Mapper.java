package com.sb.solutions.web.loan.v1.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.sb.solutions.core.enums.LoanType;
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
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.ProductUtils;
import com.sb.solutions.web.common.stage.dto.StageDto;
import com.sb.solutions.web.common.stage.mapper.StageMapper;
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

    public Mapper(@Autowired StageMapper stageMapper,
        @Autowired ApprovalLimitService approvalLimitService) {
        this.stageMapper = stageMapper;
        this.approvalLimitService = approvalLimitService;
    }

    public CustomerLoan actionMapper(StageDto loanActionDto, CustomerLoan customerLoan,
        User currentUser) {
        if ((!loanActionDto.getDocAction().equals(DocAction.PULLED)) && (!loanActionDto
            .getDocAction()
            .equals(DocAction.TRANSFER))) {
            Preconditions.checkArgument(
                customerLoan.getCurrentStage().getToUser().getId() == currentUser.getId(),
                "Sorry this document is not under you!!");
        }
        if (loanActionDto.getDocAction().equals(DocAction.APPROVED)) {
            if(customerLoan.getLoanType() == LoanType.PARTIAL_SETTLEMENT_LOAN || customerLoan.getLoanType() == LoanType.ENHANCED_LOAN) {
                customerLoan.getProposal().setExistingLimit(customerLoan.getProposal().getProposedLimit());
            }
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
        if (loanActionDto.getDocAction().equals(DocAction.FORWARD)
            || loanActionDto.getDocAction().equals(DocAction.BACKWARD)) {
            if (customerLoan.getDocumentStatus().equals(DocStatus.UNDER_REVIEW) || customerLoan
                .getDocumentStatus().equals(DocStatus.PENDING)) {
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
        return customerLoan;
    }

    private LoanStage loanStages(StageDto stageDto, List previousList, Long createdBy,
        StageDto currentStage, UserDto currentUser, CustomerLoan customerLoan) {
        if (stageDto.getDocAction().equals(DocAction.NOTED)) {
            customerLoan.setNotedBy(currentUser.getId());
        } else if (currentStage.getDocAction().equals(DocAction.CLOSED)
            || currentStage.getDocAction().equals(DocAction.APPROVED)
            || currentStage.getDocAction().equals(DocAction.REJECT)) {

            logger.error("Error while performing the action");

            throw new RuntimeException(
                "Cannot Perform the action. Document has been " + currentStage.getDocAction());
        }
        // TODO: Separate alert message for no user and disabled user
        if (stageDto.getDocAction().equals(DocAction.FORWARD)) {
            if (stageDto.getToRole() == null || stageDto.getToUser() == null) {
                logger.error("Error while performing the action");
                throw new RuntimeException("There is no user created in the role or is  disabled. Please contact admin.");
            }
            // Check loan flags
            List<CustomerLoanFlag> loanFlags = customerLoan.getLoanHolder().getLoanFlags()
                .stream()
                .filter(f -> f.getCustomerLoanId() != null && f.getCustomerLoanId().equals(customerLoan.getId()))
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
}

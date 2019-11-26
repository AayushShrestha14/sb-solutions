package com.sb.solutions.web.loan.v1.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sb.solutions.api.approvallimit.emuns.LoanApprovalType;
import com.sb.solutions.api.approvallimit.entity.ApprovalLimit;
import com.sb.solutions.api.approvallimit.service.ApprovalLimitService;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.StatisticDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.productMode.entity.ProductMode;
import com.sb.solutions.api.productMode.service.ProductModeService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.Product;
import com.sb.solutions.core.enums.Status;
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
    private final ProductModeService productModeService;


    public Mapper(@Autowired StageMapper stageMapper,
        @Autowired ApprovalLimitService approvalLimitService,
        @Autowired ProductModeService productModeService) {
        this.stageMapper = stageMapper;
        this.approvalLimitService = approvalLimitService;
        this.productModeService = productModeService;
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
            ProductMode productMode = productModeService.getByProduct(Product.DMS, Status.ACTIVE);
            if (productMode == null) {

                ApprovalLimit approvalLimit = approvalLimitService
                    .getByRoleAndLoan(currentUser.getRole().getId(),
                        customerLoan.getLoan().getId(), customerLoan.getLoanCategory());
                if (approvalLimit == null) {
                    throw new RuntimeException("Authority Limit Error");
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
        if (stageDto.getDocAction().equals(DocAction.FORWARD)) {
            if (stageDto.getToRole() == null || stageDto.getToUser() == null) {
                logger.error("Error while performing the action");
                throw new RuntimeException("No user present of selected To:");
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
                barChart.getSeries().add(series);
            });
            charts.add(barChart);
        }
        return charts;
    }
}

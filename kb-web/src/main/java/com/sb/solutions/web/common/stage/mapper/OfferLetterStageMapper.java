package com.sb.solutions.web.common.stage.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sb.solutions.api.loan.OfferLetterStage;
import com.sb.solutions.api.loan.entity.CustomerOfferLetter;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.service.RoleService;
import com.sb.solutions.api.stage.entity.Stage;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.web.common.stage.dto.StageDto;

@Component
public class OfferLetterStageMapper {

    private static final Logger logger = LoggerFactory.getLogger(OfferLetterStageMapper.class);
    private final RoleService roleService;
    private final UserService userService;

    public OfferLetterStageMapper(
        @Autowired RoleService roleService,
        @Autowired UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public CustomerOfferLetter actionMapper(StageDto stageDto,
        CustomerOfferLetter customerOfferLetter, User user, Long loanCreatedBranch) {
        if (customerOfferLetter.getIsOfferLetterApproved()) {
            logger.error("Restrict to take action on approved offer letter{}", customerOfferLetter);
            throw new ServiceValidationException(
                "Cannot perform action offer letter is already approved");
        }
        final OfferLetterStage offerLetterStage = customerOfferLetter.getOfferLetterStage();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        List previousList = customerOfferLetter.getPreviousList();
        List previousListTemp = new ArrayList();

        if (offerLetterStage != null) {

            Map<String, String> tempLoanStage = objectMapper
                .convertValue(offerLetterStage, Map.class);
            try {
                previousList.forEach(p -> {
                    try {
                        Map<String, String> previous = objectMapper.convertValue(p, Map.class);

                        previousListTemp.add(objectMapper.writeValueAsString(previous));
                    } catch (JsonProcessingException e) {
                        logger.error("Failed to handle JSON data {}", e.getMessage());
                        throw new RuntimeException("Failed to handle JSON data");
                    }
                });
                String jsonValue = objectMapper.writeValueAsString(tempLoanStage);
                previousListTemp.add(jsonValue);
            } catch (JsonProcessingException e) {
                logger.error("Failed to Get Stage data {}", e.getMessage());
                throw new RuntimeException("Failed to Get Stage data");
            }
        }
        customerOfferLetter.setDocStatus(stageDto.getDocumentStatus());
        customerOfferLetter.setOfferLetterStageList(previousListTemp.toString());
        OfferLetterStage newStage = this
            .offerLetterStage(stageDto, user, offerLetterStage, loanCreatedBranch, previousList);
        customerOfferLetter.setOfferLetterStage(newStage);
        if (stageDto.getDocumentStatus().equals(DocStatus.APPROVED)) {
            customerOfferLetter.setIsOfferLetterApproved(true);
        }
        return customerOfferLetter;
    }


    private OfferLetterStage offerLetterStage(StageDto stageDto, User user,
        OfferLetterStage currentStage, Long loanCreatedBranchId, List previousList) {
        currentStage.setFromUser(user);
        currentStage.setFromRole(user.getRole());
        currentStage.setComment(stageDto.getComment());
        currentStage.setDocAction(stageDto.getDocAction());
        switch (stageDto.getDocAction()) {
            case FORWARD:
                Role r = roleService.getRoleCAD();
                currentStage.setToRole(r);
                User u = new User();
                u.setId(stageDto.getToUser().getId());
                currentStage.setToUser(u);
                logger.info("forward offer letter{}", currentStage);
                break;

            case BACKWARD:
                if (!previousList.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    for (Object obj : previousList) {
                        Stage maker = objectMapper.convertValue(obj, Stage.class);
                        if (maker.getFromUser().getId().equals(currentStage.getCreatedBy())) {
                            currentStage.setToRole(maker.getFromRole());
                            try {
                                final List<User> users = userService
                                    .findByRoleAndBranchId(maker.getFromRole().getId(),
                                        loanCreatedBranchId);
                                final List<Long> userIdList = users.stream().map(User::getId)
                                    .collect(Collectors.toList());
                                if (userIdList.contains(currentStage.getCreatedBy())) {
                                    java.util.Optional<User> userOptional = users.stream().
                                        filter(p -> p.getId().equals(currentStage.getCreatedBy())).
                                        findFirst();
                                    currentStage.setToUser(
                                        objectMapper.convertValue(userOptional.get(), User.class));
                                } else {
                                    currentStage
                                        .setToUser(
                                            objectMapper.convertValue(users.get(0), User.class));
                                }


                            } catch (Exception e) {
                                logger.error("Error occurred while mapping stage", e);
                            }
                        }
                    }
                } else {
                    currentStage.setToUser(currentStage.getFromUser());
                    currentStage.setToRole(currentStage.getToRole());
                }
                logger.info("Backward offer letter{}", currentStage);
                break;

            case APPROVED:
                currentStage.setToRole(user.getRole());
                currentStage.setToUser(user);
                logger.info("Approved offer letter{}", currentStage);
                break;
            default:
        }
        return currentStage;
    }

}

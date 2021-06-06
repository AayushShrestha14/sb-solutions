package com.sb.solutions.web.common.stage.mapper;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.web.common.stage.dto.StageDto;
import com.sb.solutions.web.user.dto.RoleDto;
import com.sb.solutions.web.user.dto.UserDto;

/**
 * @author Rujan Maharjan on 6/12/2019
 */

@Component
public class StageMapper {

    private static final Logger logger = LoggerFactory.getLogger(StageMapper.class);

    private final UserService userService;

    public StageMapper(@Autowired UserService userService) {
        this.userService = userService;
    }


    public <T> T mapper(StageDto stageDto, List previousList, Class<T> classType, Long createdBy,
        StageDto currentStage, UserDto currentUser, CustomerLoan customerLoan) {
        ObjectMapper objectMapper = new ObjectMapper();
        User loggedUser = userService.getAuthenticatedUser();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        currentStage.setDocAction(stageDto.getDocAction());
        currentStage.setComment(stageDto.getComment());

        if (!stageDto.getDocAction().equals(DocAction.TRANSFER) && !stageDto.getDocAction()
            .equals(DocAction.PULLED)) {
            currentStage.setFromUser(currentUser);
            currentStage.setFromRole(currentUser.getRole());
        } else {
            currentStage.setFromUser(currentStage.getToUser());
            currentStage.setFromRole(currentStage.getToRole());
            currentStage.setComment(
                "Transfer By " + loggedUser.getRole().getRoleName() + ". " + stageDto.getComment());
        }

        if (stageDto.getDocAction().equals(DocAction.PULLED)) {
            currentStage.setComment("PULLED");
            currentStage.setToUser(currentUser);
            currentStage.setToRole(currentUser.getRole());
            logger.info("pulled document {}", customerLoan, currentStage);
        } else {
            currentStage.setToUser(stageDto.getToUser());
            currentStage.setToRole(stageDto.getToRole());
        }

        if (stageDto.getDocAction().equals(DocAction.BACKWARD)) {
            currentStage = this
                .sendBackward(previousList, currentStage, currentUser, createdBy, customerLoan);
        }

        if (stageDto.getDocAction().equals(DocAction.APPROVED)
            || stageDto.getDocAction().equals(DocAction.CLOSED)
            || stageDto.getDocAction().equals(DocAction.REJECT)
            || stageDto.getDocAction().equals(DocAction.NOTED)) {
            currentStage = this.approvedCloseReject(currentStage, currentUser);
        }

        return objectMapper.convertValue(currentStage, classType);

    }


    private StageDto sendBackward(List previousList, StageDto currentStage, UserDto currentUser,
        Long createdBy, CustomerLoan customerLoan) {
        List<User> makers = userService
            .findByRoleTypeAndBranchIdAndStatusActive(RoleType.MAKER,
                customerLoan.getBranch().getId());
        if (makers == null || makers.isEmpty()) {
            throw new RuntimeException("No active Maker User Exists");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        int size = previousList.size();

        UserDto targetMakerUser = null;
        RoleDto targerMakerRole = null;
        for (int i = size - 1; i >= 0; i--) {
            StageDto stage = objectMapper.convertValue(previousList.get(i), StageDto.class);
            if (stage.getFromRole().getRoleType() == RoleType.MAKER) {
                UserDto userDto = stage.getFromUser();
                // check maker is active or not
                Optional<User> maker = makers.stream().findAny()
                    .filter(user -> user.getId() == userDto.getId());

                if (maker.isPresent()) {
                    // verifed that maker user is still maker user for paticular branch
                    targetMakerUser = userDto;
                    targerMakerRole = stage.getFromRole();
                } else {
                    /*
                     loan maker is no more maker user, he might get promoted with other role, so check
                     whether loan creator is still maker user, if he/she is assign other wise pick  any one active maker user
                     */
                    maker = makers.stream().findAny()
                        .filter(user -> user.getId() == createdBy);
                    if (maker.isPresent()) {
                        targetMakerUser = objectMapper.convertValue(maker.get(), UserDto.class);
                        targerMakerRole = objectMapper
                            .convertValue(maker.get().getRole(), RoleDto.class);

                    } else {
                        targetMakerUser = objectMapper.convertValue(makers.get(0), UserDto.class);
                        targerMakerRole =
                            objectMapper.convertValue(makers.get(0).getRole(), RoleDto.class);
                    }
                }
                break;
            }

        }
        currentStage.setToUser(targetMakerUser);
        currentStage.setToRole(targerMakerRole);
        return currentStage;
    }

    private StageDto approvedCloseReject(StageDto currentStage, UserDto currentUser) {
        currentStage.setToRole(currentUser.getRole());
        currentStage.setToUser(currentUser);
        return currentStage;
    }
}

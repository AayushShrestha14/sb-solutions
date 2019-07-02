package com.sb.solutions.web.common.stage.mapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
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
        StageDto currentStage, UserDto currentUser) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        currentStage.setFromUser(currentUser);
        currentStage.setFromRole(currentUser.getRole());
        currentStage.setToUser(stageDto.getToUser());
        currentStage.setToRole(stageDto.getToRole());
        currentStage.setDocAction(stageDto.getDocAction());
        currentStage.setComment(stageDto.getComment());
        if (stageDto.getDocAction().equals(DocAction.BACKWARD)) {
            currentStage = this.sendBackward(previousList, currentStage, currentUser, createdBy);
        }

        if (stageDto.getDocAction().equals(DocAction.APPROVED)
            || stageDto.getDocumentStatus().equals(DocStatus.CLOSED)
            || stageDto.getDocumentStatus().equals(DocStatus.REJECTED)) {
            currentStage = this.approvedCloseReject(currentStage, currentUser);
        }

        return objectMapper.convertValue(currentStage, classType);

    }


    private StageDto sendBackward(List previousList, StageDto currentStage, UserDto currentUser,
        Long createdBy) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        for (Object obj : previousList) {
            StageDto maker = objectMapper.convertValue(obj, StageDto.class);
            if (maker.getFromUser().getId().equals(createdBy)) {
                RoleDto r = new RoleDto();
                r.setId(maker.getFromRole().getId());
                currentStage.setToRole(r);
                try {
                    final List<User> users = userService
                        .findByRoleAndBranch(r.getId(), userService.getRoleAccessFilterByBranch());

                    currentStage.setToUser(objectMapper.convertValue(users.get(0), UserDto.class));

                } catch (Exception e) {
                    logger.error("Error occurred while mapping stage", e);
                }
            }
        }
        if (previousList.isEmpty()) {
            currentStage.setToUser(currentStage.getFromUser());
            currentStage.setToRole(currentStage.getToRole());
        }
        return currentStage;
    }

    private StageDto approvedCloseReject(StageDto currentStage, UserDto currentUser) {
        currentStage.setToRole(currentUser.getRole());
        currentStage.setToUser(currentUser);
        return currentStage;
    }
}

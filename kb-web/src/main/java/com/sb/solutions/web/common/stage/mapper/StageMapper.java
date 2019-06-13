package com.sb.solutions.web.common.stage.mapper;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.web.common.stage.dto.StageDto;
import com.sb.solutions.web.user.dto.RoleDto;
import com.sb.solutions.web.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Rujan Maharjan on 6/12/2019
 */

@Component
public class StageMapper {


    @Autowired
    UserService userService;


    public StageMapper() {
    }


    public <T> T mapper(StageDto stageDto, List previousList, Class<T> classType, Long createdBy, StageDto currentStage, UserDto currentUser) throws IllegalAccessException, InstantiationException {
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

        T o = objectMapper.convertValue(currentStage, classType);
        return o;
    }


    public StageDto sendBackward(List previousList, StageDto currentStage, UserDto currentUser, Long createdBy) {
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
                    List<User> users = userService.findByRoleAndBranch(r.getId(), currentUser.getBranch().getId());

                    currentStage.setToUser(objectMapper.convertValue(users.get(0), UserDto.class));

                } catch (Exception e) {
                }
            }
        }
        if (previousList.isEmpty()) {
            currentStage.setToUser(currentStage.getFromUser());
            currentStage.setToRole(currentStage.getToRole());
        }
        return currentStage;
    }


}

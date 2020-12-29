package com.sb.solutions.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import com.sb.solutions.api.authorization.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.dto.CadStageDto;
import com.sb.solutions.dto.StageDto;
import com.sb.solutions.entity.CadStage;
import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;

/**
 * @author : Rujan Maharjan on  12/7/2020
 **/

@Component
public class CadStageMapper {

    private final ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper().
        setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public String addPresentStageToPreviousList(List previousList, CadStage cadStage) {
        List previousListTemp = new ArrayList();
        if (cadStage != null) {

            Map<String, String> tempCadStage = objectMapper
                .convertValue(cadStage, Map.class);
            try {
                previousList.forEach(p -> {
                    try {
                        Map<String, String> previous = objectMapper.convertValue(p, Map.class);

                        previousListTemp.add(objectMapper.writeValueAsString(previous));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to handle JSON data");
                    }
                });
                String jsonValue = objectMapper.writeValueAsString(tempCadStage);
                previousListTemp.add(jsonValue);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to Get Stage data");
            }
        }
        return previousListTemp.toString();
    }


    public StageDto cadAction(CadStageDto requestedStage,
        CustomerApprovedLoanCadDocumentation oldData, User currentUser) {
        StageDto stageDto = new StageDto();
        stageDto.setPreviousList(
            addPresentStageToPreviousList(oldData.getPreviousList(), oldData.getCadCurrentStage()));
        CadStage cadStage = oldData.getCadCurrentStage();
        cadStage.setFromRole(currentUser.getRole());
        cadStage.setFromUser(currentUser);
        cadStage.setComment(requestedStage.getComment());
        User user = new User();
        Role role = new Role();
        switch (requestedStage.getDocAction()) {
            case FORWARD:
                user.setId(requestedStage.getToUser().getId());
                role.setId(requestedStage.getToRole().getId());
                cadStage.setToUser(user);
                cadStage.setToRole(role);
                break;
            case BACKWARD:

                break;
            case APPROVED:
                cadStage.setToUser(currentUser);
                cadStage.setToRole(currentUser.getRole());
                break;
        }
        stageDto.setCadStage(cadStage);
        return stageDto;
    }
}

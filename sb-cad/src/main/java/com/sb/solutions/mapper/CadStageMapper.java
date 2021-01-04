package com.sb.solutions.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.sb.solutions.api.authorization.entity.Role;
import com.sb.solutions.api.loan.dto.LoanStageDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.dto.CadStageDto;
import com.sb.solutions.dto.StageDto;
import com.sb.solutions.entity.CadStage;
import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;
import com.sb.solutions.enums.CADDocAction;
import com.sb.solutions.enums.CadDocStatus;

/**
 * @author : Rujan Maharjan on  12/7/2020
 **/

@Component
@Slf4j
public class CadStageMapper {

    private final ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper().
        setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .setDateFormat(new SimpleDateFormat(AppConstant.DATE_FORMAT));

    private final UserService userService;

    public CadStageMapper(UserService userService) {
        this.userService = userService;
    }

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

        cadStage.setComment(requestedStage.getComment());
        User user = new User();
        Role role = new Role();
        switch (requestedStage.getDocAction()) {
            case FORWARD:
                cadStage.setFromRole(currentUser.getRole());
                cadStage.setFromUser(currentUser);
                user.setId(requestedStage.getToUser().getId());
                role.setId(requestedStage.getToRole().getId());
                cadStage.setDocAction(CADDocAction.FORWARD);
                cadStage.setToUser(user);
                cadStage.setToRole(role);
                break;
            case BACKWARD:

                if (oldData.getPreviousList().stream().filter(f -> f.getDocAction().equals(
                    DocAction.FORWARD) || f.getDocAction().equals(
                    DocAction.BACKWARD)).collect(Collectors.toList()).isEmpty()) {
                    CustomerLoan oldDataCustomerLoan = oldData.getAssignedLoan().get(0);
                    Map<String, Long> creator = this
                        .getLoanMaker(oldDataCustomerLoan.getPreviousStageList(),
                            oldDataCustomerLoan.getBranch().getId());
                    user.setId(creator.get("userId"));
                    role.setId(creator.get("roleId"));


                } else {
                    user.setId(oldData.getCadCurrentStage().getFromUser().getId());
                    role.setId(oldData.getCadCurrentStage().getFromUser().getRole().getId());
                }
                cadStage.setDocAction(CADDocAction.BACKWARD);
                cadStage.setFromRole(currentUser.getRole());
                cadStage.setFromUser(currentUser);
                cadStage.setToUser(user);
                cadStage.setToRole(role);
                break;
            case OFFER_APPROVED:

                cadStage.setFromRole(currentUser.getRole());
                cadStage.setFromUser(currentUser);
                cadStage.setDocAction(CADDocAction.OFFER_APPROVED);
                stageDto.setCadDocStatus(CadDocStatus.OFFER_APPROVED);
                cadStage.setToUser(currentUser);
                cadStage.setToRole(currentUser.getRole());
                break;
            case LEGAL_APPROVED:
                cadStage.setFromRole(currentUser.getRole());
                cadStage.setFromUser(currentUser);
                cadStage.setDocAction(CADDocAction.LEGAL_APPROVED);
                stageDto.setCadDocStatus(CadDocStatus.LEGAL_APPROVED);
                cadStage.setToUser(currentUser);
                cadStage.setToRole(currentUser.getRole());
                break;
            case DISBURSEMENT_APPROVED:
                cadStage.setFromRole(currentUser.getRole());
                cadStage.setFromUser(currentUser);
                stageDto.setCadDocStatus(CadDocStatus.DISBURSEMENT_APPROVED);
                cadStage.setDocAction(CADDocAction.DISBURSEMENT_APPROVED);
                cadStage.setToUser(currentUser);
                cadStage.setToRole(currentUser.getRole());
                break;

        }
        stageDto.setCadStage(cadStage);
        return stageDto;
    }


    private Map<String, Long> getLoanMaker(String list, Long branchID) {
        Map<String, Long> map = new HashMap<>();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        try {
            List<LoanStageDto> mapPreviousList = objectMapper.readValue(list,
                typeFactory.constructCollectionType(List.class, LoanStageDto.class));
            List<LoanStageDto> makerList = mapPreviousList.stream()
                .filter(a -> a.getFromRole().getRoleType().equals(RoleType.MAKER)).collect(
                    Collectors.toList());
            final List<User> users = userService
                .findByRoleAndBranchId(makerList.get(0).getFromRole().getId(), branchID);
            final List<Long> userIdList = users.stream().map(User::getId)
                .collect(Collectors.toList());
            if (userIdList.contains(makerList.get(0).getFromUser().getId())) {
                map.put("userId", makerList.get(0).getFromUser().getId());
                map.put("roleId", makerList.get(0).getFromRole().getId());
            } else {
                map.put("userId", users.get(0).getId());
                map.put("roleId", users.get(0).getRole().getId());
            }


        } catch (Exception e) {
            log.error("unable to get users for backward ", e);

        }
        return map;
    }

}

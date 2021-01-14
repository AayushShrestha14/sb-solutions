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
import org.springframework.util.ObjectUtils;

import com.sb.solutions.api.authorization.entity.Role;
import com.sb.solutions.api.loan.dto.LoanStageDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.user.dto.UserDto;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.AppConstant;
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
                if (!ObjectUtils.isEmpty(previousList)) {
                    previousList.forEach(p -> {
                        try {
                            Map<String, String> previous = objectMapper.convertValue(p, Map.class);

                            previousListTemp.add(objectMapper.writeValueAsString(previous));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException("Failed to handle JSON data");
                        }
                    });
                }
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
                stageDto.setCadDocStatus(requestedStage.getDocumentStatus());
                cadStage.setToUser(user);
                cadStage.setToRole(role);
                break;
            case BACKWARD:
                if ((cadStage.getDocAction().equals(CADDocAction.ASSIGNED)) || cadStage
                    .getFromRole().getRoleType().equals(RoleType.CAD_SUPERVISOR) || cadStage
                    .getFromRole().getRoleType().equals(RoleType.CAD_ADMIN)) {
                    CustomerLoan oldDataCustomerLoan = oldData.getAssignedLoan().get(0);
                    Map<String, Long> creator = this
                        .getLoanMaker(oldDataCustomerLoan.getPreviousStageList(),
                            oldDataCustomerLoan.getBranch().getId());
                    user.setId(creator.get("userId"));
                    role.setId(creator.get("roleId"));
                    cadStage.setToUser(user);
                    cadStage.setToRole(role);

                } else {
                    if (requestedStage.getIsBackwardForMaker()) {
                        Map<String, Long> cadMaker = this
                            .getCADMaker(oldData.getCadStageList(),
                                oldData.getLoanHolder().getBranch().getId());
                        if (ObjectUtils.isEmpty(cadMaker.get("userId"))) {
                            cadMaker = this
                                .getLoanMaker(
                                    oldData.getAssignedLoan().get(0).getPreviousStageList(),
                                    oldData.getAssignedLoan().get(0).getBranch().getId());
                        }
                        user.setId(cadMaker.get("userId"));
                        role.setId(cadMaker.get("roleId"));
                        cadStage.setToUser(user);
                        cadStage.setToRole(role);
                    } else {
                        cadStage.setToUser(cadStage.getFromUser());
                        cadStage.setToRole(cadStage.getFromRole());
                    }
                }
                cadStage.setDocAction(CADDocAction.BACKWARD);

                cadStage.setFromRole(currentUser.getRole());
                cadStage.setFromUser(currentUser);
                stageDto.setCadDocStatus(requestedStage.getDocumentStatus());
                break;
            case OFFER_APPROVED:
                cadStage.setFromRole(currentUser.getRole());
                cadStage.setFromUser(currentUser);
                cadStage.setDocAction(CADDocAction.OFFER_APPROVED);
                stageDto.setCadDocStatus(CadDocStatus.OFFER_APPROVED);
                //set RM
                Map<String, Long> cadMaker = this
                    .getCADMaker(oldData.getCadStageList(),
                        oldData.getLoanHolder().getBranch().getId());
                if (ObjectUtils.isEmpty(cadMaker.get("userId"))) {
                    cadMaker = this
                        .getLoanMaker(
                            oldData.getAssignedLoan().get(0).getPreviousStageList(),
                            oldData.getAssignedLoan().get(0).getBranch().getId());
                }
                user.setId(cadMaker.get("userId"));
                role.setId(cadMaker.get("roleId"));
                cadStage.setToUser(user);
                cadStage.setToRole(role);
                break;
            case LEGAL_APPROVED:
                cadStage.setFromRole(currentUser.getRole());
                cadStage.setFromUser(currentUser);
                cadStage.setDocAction(CADDocAction.LEGAL_APPROVED);
                stageDto.setCadDocStatus(CadDocStatus.DISBURSEMENT_PENDING);
                //set to user CAD
                Map<String, Long> cadUser = this
                    .getCADUser(oldData.getPreviousList(),
                        oldData.getLoanHolder().getBranch().getId());
                user.setId(cadUser.get("userId"));
                role.setId(cadUser.get("roleId"));
                cadStage.setToUser(user);
                cadStage.setToRole(role);
                break;
            case DISBURSEMENT_APPROVED:
                cadStage.setFromRole(currentUser.getRole());
                cadStage.setFromUser(currentUser);
                stageDto.setCadDocStatus(CadDocStatus.DISBURSEMENT_APPROVED);
                cadStage.setDocAction(CADDocAction.DISBURSEMENT_APPROVED);
                Map<String, Long> cadMaker1 = this
                    .getCADMaker(oldData.getCadStageList(),
                        oldData.getLoanHolder().getBranch().getId());
                if (ObjectUtils.isEmpty(cadMaker1.get("userId"))) {
                    cadMaker1 = this
                        .getLoanMaker(
                            oldData.getAssignedLoan().get(0).getPreviousStageList(),
                            oldData.getAssignedLoan().get(0).getBranch().getId());
                }
                user.setId(cadMaker1.get("userId"));
                role.setId(cadMaker1.get("roleId"));
                cadStage.setToUser(user);
                cadStage.setToRole(role);
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


    private Map<String, Long> getCADMaker(String list, Long branchID) {
        Map<String, Long> map = new HashMap<>();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        try {
            List<CadStage> mapPreviousList = objectMapper.readValue(list,
                typeFactory.constructCollectionType(List.class, CadStage.class));
            List<CadStage> makerList = mapPreviousList.stream()
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

    private Map<String, Long> getCADUser(List<CadStage> list, Long branchID) {
        Map<String, Long> map = new HashMap<>();
        try {
            List<CadStage> mapPreviousList = list;
            List<CadStage> makerList = mapPreviousList.stream()
                .filter(a -> a.getFromRole().getRoleName().equalsIgnoreCase("CAD")).collect(
                    Collectors.toList());
            //todo check user still exist in this role
            if (makerList.isEmpty() || ObjectUtils.isEmpty(makerList)) {
                List<UserDto> userByRoleCad = userService.getUserByRoleCad();
                map.put("userId", userByRoleCad.get(0).getId());
                map.put("roleId", userByRoleCad.get(0).getRole().getId());
            } else {
                map.put("userId", makerList.get(0).getFromUser().getId());
                map.put("roleId", makerList.get(0).getFromRole().getId());
            }


        } catch (Exception e) {
            log.error("unable to get users for backward ", e);

        }
        return map;
    }
}

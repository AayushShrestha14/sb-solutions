package com.sb.solutions.web.user;

import com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy;
import com.sb.solutions.api.rolePermissionRight.service.RoleHierarchyService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.web.user.dto.RoleHierarchyDto;
import com.sb.solutions.web.user.mapper.RoleHierarchyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rujan Maharjan on 5/13/2019
 */

@RestController
@RequestMapping("/v1/role-hierarchy")
public class RoleHierarchyController {


    private final Logger logger = LoggerFactory.getLogger(RoleHierarchyController.class);


    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    RoleHierarchyMapper roleHierarchyMapper;

    @Autowired
    RoleHierarchyService roleHierarchyService;

    @Autowired
    UserService userService;


    @PostMapping
    public ResponseEntity<?> saveRoleHierarchyList(@RequestBody List<RoleHierarchyDto> roleHierarchyDtoList, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        List<RoleHierarchy> roleHierarchies = roleHierarchyService.SaveList(roleHierarchyMapper.mapDtosToEntities(roleHierarchyDtoList));
        if (roleHierarchies.isEmpty()) {
            logger.error("Error while saving Role_Hierarchy {}", roleHierarchyDtoList);
            return new RestResponseDto()
                    .failureModel("Error occurred while saving Role_Hierarchy " + roleHierarchyDtoList);
        }
        return new RestResponseDto().successModel(roleHierarchyMapper.mapEntitiesToDtos(roleHierarchies));
    }


    @GetMapping("/all")
    public ResponseEntity<?> getRoleHierarchyList() {
        return new RestResponseDto().successModel(roleHierarchyMapper.mapEntitiesToDtos(roleHierarchyService.findAll()));
    }

    @GetMapping("/getForward")
    public ResponseEntity<?> getRoleHierarchyListPerRoleForward() {
        User u = userService.getAuthenticated();
        return new RestResponseDto().successModel(roleHierarchyService.roleHierarchyByCurrentRole(u.getRole().getId()));
    }

    @GetMapping("/getBackward")
    public ResponseEntity<?> getRoleHierarchyListPerRoleBackward() {
        User u = userService.getAuthenticated();
        return new RestResponseDto().successModel(roleHierarchyService.roleHierarchyByCurrentRoleBackward(u.getRole().getId()));
    }
}

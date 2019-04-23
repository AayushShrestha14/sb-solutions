package com.sb.solutions.web.user.controller;

import com.sb.solutions.api.rolePermissionRight.entity.RolePermissionRights;
import com.sb.solutions.api.rolePermissionRight.service.RightService;
import com.sb.solutions.api.rolePermissionRight.service.RolePermissionRightService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Rujan Maharjan on 3/28/2019
 */

@RestController
@RequestMapping("/v1/roleRightPermission")
public class RoleRightPermissionController {

    @Autowired
    RolePermissionRightService rolePermissionRightService;

    @Autowired
    RightService rightService;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveRolePermRight(@Valid @RequestBody List<RolePermissionRights> rpr, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        rolePermissionRightService.saveList(rpr);
        return new RestResponseDto().successModel(null);

    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<?> getRolePermission(@PathVariable Long id) {

        return new RestResponseDto().successModel(rolePermissionRightService.getByRoleId(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rights")
    public ResponseEntity<?> getRights() {

        return new RestResponseDto().successModel(rightService.getAll());
    }
}
